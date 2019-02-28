package net.treebear.kwifimanager.config;

/**
 * 所有key值存储
 *
 * @important 请不要通过实现的方式引入
 */
public interface Keys {
    //----------------------request传输key--------------------//
    String MOBILE = "mobile";
    String TYPE = "type";
    String VERIFY_CODE = "vcode";
    String PASSWORD = "passwd";

    //----------------------intent传输key--------------------//
    String TITLE = "title";
    String TARGET = "target";
    String CONFIRM_BUTTON_TEXT = "confirm_button_text";
}
