package com.chibik.perf.report;

public class HTMLTableBuilder {

    private int columns;
    private Props props;
    private final StringBuilder table = new StringBuilder();
    public static String TABLE_START_BORDER = "<table border=\"1\">";
    public static String TABLE_END = "</table>";
    public static String CAPTION_START = "<caption>";
    public static String CAPTION_END = "</caption>";
    public static String HEADER_CELL_START = "<th>";
    public static String HEADER_CELL_END = "</th>";
    public static String ROW_START = "<tr>";
    public static String ROW_END = "</tr>";
    public static String ROW_CELL_START = "<td>";
    public static String ROW_CELL_END = "</td>";

    public static class Props {
        public boolean border = true;
        String caption = "";

        public Props border(boolean border) {
            this.border = border;
            return this;
        }

        public Props caption(String caption) {
            this.caption = caption;
            return this;
        }
    }

    public HTMLTableBuilder(Props props, String... headerNames) {
        this.props = props;
        this.columns = headerNames.length;

        addTableHeader(headerNames);
    }

    private void addTableHeader(String... values) {
        if (values.length != columns) {
            throw new RuntimeException("Invalid number of values for a row");
        }

        table.append(ROW_START).append("\n");
        for (String value : values) {
            table.append("\t").append(HEADER_CELL_START).append(prepare(value)).append(HEADER_CELL_END).append("\n");
        }
        table.append(ROW_END).append("\n");
    }

    public void addRowValues(String... values) {
        if (values.length != columns) {
            throw new RuntimeException("Invalid number of values for a row");
        }

        table.append(ROW_START).append("\n");
        for (String value : values) {
            table.append("\t").append(ROW_CELL_START).append(prepare(value)).append(ROW_CELL_END).append("\n");
        }
        table.append(ROW_END).append("\n");
    }

    private String prepare(String text) {
        return text.replace("\r", "").replace("\n", "<br/>");
    }

    public String build() {
        StringBuilder htmlTable = new StringBuilder();
        htmlTable.append(TABLE_START_BORDER).append("\n");
        htmlTable.append(CAPTION_START).append(props.caption).append(CAPTION_END).append("\n");
        htmlTable.append(table);
        htmlTable.append(TABLE_END);
        return htmlTable.toString();
    }
}