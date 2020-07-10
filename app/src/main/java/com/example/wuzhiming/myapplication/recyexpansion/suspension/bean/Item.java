package com.example.wuzhiming.myapplication.recyexpansion.suspension.bean;


public class Item {
    private String content;

    public Item(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item){
            Item item = (Item)obj;
            return content.equals(item.getContent());
        }
        return false;
    }

    public Item copy(){
        return new Item(content);
    }
}
