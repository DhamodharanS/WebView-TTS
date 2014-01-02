package com.example.webpagereader;

enum EBtnSts {
    E_PLAY_BTN("ePlayBtn", 1);

    private final String value;
    private final int id;

    private EBtnSts(String value, int id) {
        this.value = value;
        this.id = id;
    }

    private final EBtnSts[] VALUES = values();
}