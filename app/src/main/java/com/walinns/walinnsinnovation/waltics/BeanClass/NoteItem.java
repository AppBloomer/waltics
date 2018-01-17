package com.walinns.walinnsinnovation.waltics.BeanClass;

/**
 * Created by walinnsinnovation on 12/01/18.
 */

public class NoteItem {

    String note_text;
    String note_cat;
    String note_date;
    int note_id;

    public NoteItem(String note_text_,String note_cat_,String note_date_){
        this.note_text = note_text_;
        this.note_cat = note_cat_;
        this.note_date = note_date_;

    }
    public NoteItem(String note_text_,String note_cat_,String note_date_,int note_id_){
        this.note_text = note_text_;
        this.note_cat = note_cat_;
        this.note_date = note_date_;
        this.note_id = note_id_;

    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public int getNote_id() {
        return note_id;
    }

    public String getNote_cat() {
        return note_cat;
    }

    public String getNote_date() {
        return note_date;
    }

    public void setNote_cat(String note_cat) {
        this.note_cat = note_cat;
    }

    public void setNote_date(String note_date) {
        this.note_date = note_date;
    }

    public String getNote_text() {
        return note_text;
    }
}
