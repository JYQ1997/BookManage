package com.bookmanage.enums;

public enum BookTypeEnum {

    EDUCATION(1,"教育教学"),
    LEISURE(2,"休闲读物"),
    ECONOMY(3,"经济管理"),
    TECHNOLOGY(4,"工业技术"),
    FOREIGN_LANGUAGE(5,"外语教育"),
    LITERARY(6,"文学读物"),
    ART(7,"艺术设计"),
    SOCIETY(8,"社会科学"),
    COMPUTER(9,"计算机"),
    POLITICAL(10,"政治图书");

    private Integer code;
    /**
     * 描述
     */
    private String desc;

    BookTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Integer getCode(String desc) {
        BookTypeEnum[] values = BookTypeEnum.values();
        for (BookTypeEnum bookTypeEnum : values) {
            if (bookTypeEnum.getDesc().equals(desc)) {
                return bookTypeEnum.getCode();
            }
        }
        return null;
    }

    public static String getDesc(Integer code) {
        BookTypeEnum[] values = BookTypeEnum.values();
        for (BookTypeEnum bookTypeEnum : values) {
            if (bookTypeEnum.getCode().equals(code)) {
                return bookTypeEnum.getDesc();
            }
        }
        return null;
    }
}
