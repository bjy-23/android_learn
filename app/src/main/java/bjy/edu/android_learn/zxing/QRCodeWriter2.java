package bjy.edu.android_learn.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.Map;

/**
 * 消除了zxing 源码中二维码生成时的空白边间距
 */
public class QRCodeWriter2 implements Writer {

    private static final int QUIET_ZONE_SIZE = 4;

    public static int realWidth = 0;
    public static int realHeight = 0;

    @Override
    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height)
            throws WriterException {

        return encode(contents, format, width, height, null);
    }

    @Override
    public BitMatrix encode(String contents,
                            BarcodeFormat format,
                            int width,
                            int height,
                            Map<EncodeHintType,?> hints) throws WriterException {

        if (contents.length() == 0) {
            throw new IllegalArgumentException("Found empty contents");
        } else if (format != BarcodeFormat.QR_CODE) {
            throw new IllegalArgumentException("Can only encode QR_CODE, but got " + format);
        } else if (width >= 0 && height >= 0) {
            ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
            if (hints != null) {
                ErrorCorrectionLevel requestedECLevel = (ErrorCorrectionLevel)hints.get(EncodeHintType.ERROR_CORRECTION);
                if (requestedECLevel != null) {
                    errorCorrectionLevel = requestedECLevel;
                }
            }

            QRCode code = new QRCode();
            Encoder.encode(contents, errorCorrectionLevel, hints, code);
            return renderResult(code, width, height);
        } else {
            throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' + height);
        }
    }

    // Note that the input matrix uses 0 == white, 1 == black, while the output matrix uses
    // 0 == black, 255 == white (i.e. an 8 bit greyscale bitmap).
    private static BitMatrix renderResult(QRCode code, int width, int height) {
        ByteMatrix input = code.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        } else {
            int inputWidth = input.getWidth();
            int inputHeight = input.getHeight();
//            int qrWidth = inputWidth + 8;
//            int qrHeight = inputHeight + 8;
            int qrWidth = inputWidth;
            int qrHeight = inputHeight;
            int outputWidth = Math.max(width, qrWidth);
            int outputHeight = Math.max(height, qrHeight);

            int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);

            // TODO: 2019-11-21 根据  multiple * qrwidth的值重新定义绘制的像素大小
            realWidth = multiple * inputWidth;
            realHeight = multiple * inputHeight;

//            int leftPadding = (outputWidth - inputWidth * multiple) / 2;
//            int topPadding = (outputHeight - inputHeight * multiple) / 2;
            int leftPadding = 0;
            int topPadding = 0;
            BitMatrix output = new BitMatrix(realWidth, realHeight);

//            int inputY = 0;
//            for(int outputY = topPadding; inputY < inputHeight; outputY += multiple) {
//                int inputX = 0;
//
//                for(int outputX = leftPadding; inputX < inputWidth; outputX += multiple) {
//                    if (input.get(inputX, inputY) == 1) {
//                        output.setRegion(outputX, outputY, multiple, multiple);
//                    }
//
//                    ++inputX;
//                }
//
//                ++inputY;
//            }

            for (int inputY = 0, outputY = topPadding; inputY < inputHeight; inputY++, outputY += multiple) {
                // Write the contents of this row of the barcode
                for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple) {
                    if (input.get(inputX, inputY) == 1) {
                        output.setRegion(outputX, outputY, multiple, multiple);
                    }
                }
            }

            return output;
        }
    }

}
