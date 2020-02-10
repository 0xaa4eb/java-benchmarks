package com.chibik.perf.report;

public interface HTMLElement {

    static HTMLElement newLine() {
        return new HTMLNewLine();
    }

    static HTMLElement text(String text) {
        return new HTMLText(text);
    }

    String render();
}
