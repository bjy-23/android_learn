package edu.bjy.plugin.notepad;

import android.os.Parcel;
import android.os.Parcelable;

public class NotepadBean implements Parcelable {
    private String note;
    private String timestamp;
    private int id;

    public NotepadBean() {
    }

    protected NotepadBean(Parcel in) {
        note = in.readString();
        timestamp = in.readString();
        id = in.readInt();
    }

    public static final Creator<NotepadBean> CREATOR = new Creator<NotepadBean>() {
        @Override
        public NotepadBean createFromParcel(Parcel in) {
            return new NotepadBean(in);
        }

        @Override
        public NotepadBean[] newArray(int size) {
            return new NotepadBean[size];
        }
    };

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(note);
        dest.writeString(timestamp);
        dest.writeInt(id);
    }
}
