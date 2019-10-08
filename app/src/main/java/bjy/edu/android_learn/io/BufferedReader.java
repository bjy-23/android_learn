package bjy.edu.android_learn.io;

import java.io.IOException;
import java.io.Reader;

/**
 *对 源码进行分析
 */
public class BufferedReader extends Reader{
    private Reader in;

    //字符缓冲区
    private char cb[];
    // nChars 是cb缓冲区中字符的总的个数
    // nextChar 是下一个要读取的字符在cb缓冲区中的位置
    private int nChars, nextChar;

    // 表示“标记无效”。它与UNMARKED的区别是：
    // (01) UNMARKED 是压根就没有设置过标记。
    // (02) 而INVALIDATED是设置了标记，但是被标记位置太长，导致标记无效！
    private static final int INVALIDATED = -2;
    // 表示没有设置“标记”
    private static final int UNMARKED = -1;
    // “标记”
    private int markedChar = UNMARKED;
    // “标记”能标记位置的最大长度
    private int readAheadLimit = 0; /* Valid only when markedChar > 0 */

    /** If the next character is a line feed, skip it */
    private boolean skipLF = false;

    /** The skipLF flag when the mark was set */
    private boolean markedSkipLF = false;

    // 默认字符缓冲区大小
    private static int defaultCharBufferSize = 8192;
    // 默认每一行的字符个数
    private static int defaultExpectedLineLength = 80;

    /**
     * Creates a buffering character-input stream that uses an input buffer of
     * the specified size.
     *
     * @param  in   A Reader
     * @param  sz   Input-buffer size
     *
     * @exception  IllegalArgumentException  If {@code sz <= 0}
     */
    public BufferedReader(Reader in, int sz) {
        super(in);
        if (sz <= 0)
            throw new IllegalArgumentException("Buffer size <= 0");
        this.in = in;
        cb = new char[sz];
        nextChar = nChars = 0;
    }

    /**
     * Creates a buffering character-input stream that uses a default-sized
     * input buffer.
     *
     * @param  in   A Reader
     */
    public BufferedReader(Reader in) {
        this(in, defaultCharBufferSize);
    }

    /** Checks to make sure that the stream has not been closed */
    private void ensureOpen() throws IOException {
        if (in == null)
            throw new IOException("Stream closed");
    }

    /**
     * Fills the input buffer, taking the mark into account if it is valid.
     */
    // 填充缓冲区函数。有以下两种情况被调用：
    // (01) 缓冲区没有数据时，通过fill()可以向缓冲区填充数据。
    // (02) 缓冲区数据被读完，需更新时，通过fill()可以更新缓冲区的数据。
    private void fill() throws IOException {
        // dst表示“cb中填充数据的起始位置”
        int dst;
        if (markedChar <= UNMARKED) {
            /* No mark */
            dst = 0;
        } else {
            /* Marked */
            int delta = nextChar - markedChar;
            if (delta >= readAheadLimit) {
                /* Gone past read-ahead limit: Invalidate mark */
                markedChar = INVALIDATED;
                readAheadLimit = 0;
                dst = 0;
            } else {
                if (readAheadLimit <= cb.length) {
                    /* Shuffle in the current buffer */
                    System.arraycopy(cb, markedChar, cb, 0, delta);
                    markedChar = 0;
                    dst = delta;
                } else {
                    /* Reallocate buffer to accommodate read-ahead limit */
                    //
                    // Android-changed: Use the same strategy as BufferedInputStream,
                    // i.e, double the size of the buffer on each fill. Do not directly
                    // size the buffer to the readAheadLimit.
                    //
                    // char ncb[] = new char[readAheadLimit];
                    int nlength = cb.length * 2;
                    if (nlength > readAheadLimit) {
                        nlength = readAheadLimit;
                    }
                    char ncb[] = new char[nlength];
                    System.arraycopy(cb, markedChar, ncb, 0, delta);
                    cb = ncb;
                    markedChar = 0;
                    dst = delta;
                }
                nextChar = nChars = delta;
            }
        }

        int n;
        do {
            n = in.read(cb, dst, cb.length - dst);
        } while (n == 0);
        if (n > 0) {
            nChars = dst + n;
            nextChar = dst;
        }
    }

    /**
     * Reads a single character.
     *
     * @return The character read, as an integer in the range
     *         0 to 65535 (<tt>0x00-0xffff</tt>), or -1 if the
     *         end of the stream has been reached
     * @exception  IOException  If an I/O error occurs
     */
    public int read() throws IOException {
        synchronized (lock) {
            ensureOpen();
            for (;;) {
                if (nextChar >= nChars) {
                    fill();
                    if (nextChar >= nChars)
                        return -1;
                }
                if (skipLF) {
                    skipLF = false;
                    if (cb[nextChar] == '\n') {
                        nextChar++;
                        continue;
                    }
                }
                return cb[nextChar++];
            }
        }
    }

    /**
     * Reads characters into a portion of an array, reading from the underlying
     * stream if necessary.
     */
    private int read1(char[] cbuf, int off, int len) throws IOException {
        if (nextChar >= nChars) {
            /* If the requested length is at least as large as the buffer, and
               if there is no mark/reset activity, and if line feeds are not
               being skipped, do not bother to copy the characters into the
               local buffer.  In this way buffered streams will cascade
               harmlessly. */
            if (len >= cb.length && markedChar <= UNMARKED && !skipLF) {
                return in.read(cbuf, off, len);
            }
            fill();
        }
        if (nextChar >= nChars) return -1;
        if (skipLF) {
            skipLF = false;
            if (cb[nextChar] == '\n') {
                nextChar++;
                if (nextChar >= nChars)
                    fill();
                if (nextChar >= nChars)
                    return -1;
            }
        }
        int n = Math.min(len, nChars - nextChar);
        System.arraycopy(cb, nextChar, cbuf, off, n);
        nextChar += n;
        return n;
    }

    // 对read1()的封装，添加了“同步处理”和“阻塞式读取”等功能
    public int read(char cbuf[], int off, int len) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if ((off < 0) || (off > cbuf.length) || (len < 0) ||
                    ((off + len) > cbuf.length) || ((off + len) < 0)) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return 0;
            }

            int n = read1(cbuf, off, len);
            if (n <= 0) return n;
            while ((n < len) && in.ready()) {
                int n1 = read1(cbuf, off + n, len - n);
                if (n1 <= 0) break;
                n += n1;
            }
            return n;
        }
    }

    /**
     * Reads a line of text.  A line is considered to be terminated by any one
     * of a line feed ('\n'), a carriage return ('\r'), or a carriage return
     * followed immediately by a linefeed.
     *
     * @return     A String containing the contents of the line, not including
     *             any line-termination characters, or null if the end of the
     *             stream has been reached
     *
     * @exception  IOException  If an I/O error occurs
     *
     * @see java.nio.file.Files#readAllLines
     */
    // 读取一行数据。不忽略换行符
    public String readLine() throws IOException {
        return readLine(false);
    }

    /**
     * Reads a line of text.  A line is considered to be terminated by any one
     * of a line feed ('\n'), a carriage return ('\r'), or a carriage return
     * followed immediately by a linefeed.
     *
     * @param      ignoreLF  If true, the next '\n' will be skipped
     *
     * @return     A String containing the contents of the line, not including
     *             any line-termination characters, or null if the end of the
     *             stream has been reached
     *
     * @see        java.io.LineNumberReader#readLine()
     *
     * @exception  IOException  If an I/O error occurs
     */
    String readLine(boolean ignoreLF) throws IOException {
        StringBuffer s = null;
        int startChar;

        synchronized (lock) {
            ensureOpen();
            boolean omitLF = ignoreLF || skipLF;

            bufferLoop:
            for (;;) {

                if (nextChar >= nChars)
                    fill();
                if (nextChar >= nChars) { /* EOF */
                    if (s != null && s.length() > 0)
                        return s.toString();
                    else
                        return null;
                }
                boolean eol = false;
                char c = 0;
                int i;

                /* Skip a leftover '\n', if necessary */
                if (omitLF && (cb[nextChar] == '\n'))
                    nextChar++;
                skipLF = false;
                omitLF = false;

                charLoop:
                for (i = nextChar; i < nChars; i++) {
                    c = cb[i];
                    if ((c == '\n') || (c == '\r')) {
                        eol = true;
                        break charLoop;
                    }
                }

                startChar = nextChar;
                nextChar = i;

                if (eol) {
                    String str;
                    if (s == null) {
                        str = new String(cb, startChar, i - startChar);
                    } else {
                        s.append(cb, startChar, i - startChar);
                        str = s.toString();
                    }
                    //跳过读取\n、\r
                    nextChar++;
                    if (c == '\r') {
                        skipLF = true;
                    }
                    return str;
                }

                if (s == null)
                    s = new StringBuffer(defaultExpectedLineLength);
                s.append(cb, startChar, i - startChar);
            }
        }
    }

    public void close() throws IOException {
        synchronized (lock) {
            if (in == null)
                return;
            try {
                in.close();
            } finally {
                in = null;
                cb = null;
            }
        }
    }
}
