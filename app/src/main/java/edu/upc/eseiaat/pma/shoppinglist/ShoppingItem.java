package edu.upc.eseiaat.pma.shoppinglist;

/**
 * Created by arnau on 23/10/2017.
 */

public class ShoppingItem {
    private String text;
    private boolean check;

    public ShoppingItem(String text) {
        this.text = text;
        this.check = false;
    }

    public ShoppingItem(String text, boolean check) {
        this.text = text;
        this.check = check;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public void toggleChecked() {
        this.check = !this.check;
    }
}
