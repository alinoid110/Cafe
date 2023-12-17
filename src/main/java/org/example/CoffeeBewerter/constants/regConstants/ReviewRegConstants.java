package org.example.CoffeeBewerter.constants.regConstants;

public class ReviewRegConstants {
    // meeting registration func context
    public static final String SET_REVIEW_TITLE = "set_review_title";
    public static final String SET_REVIEW_DESCRIPTION = "set_review_description";
    public static final String SET_REVIEW_PLACE = "set_review_place";
    public static final String SET_REVIEW_EVENT_DATE = "set_review_event_date";
    public static final String SET_REVIEW_USER_LIMIT = "set_review_user_limit";
    public static final String SET_REVIEW_CAFE_TYPE = "set_review_cafe_type";
    public static final String SET_REVIEW_FORENAME = "set_review_forename";

    // meeting registration func texts
    public static final String SET_REVIEW_TITLE_TEXT = "Введите заголовок отзыва (оценку буквами):";
    public static final String SET_REVIEW_DESCRIPTION_TEXT = "Введите описание (комментарий) к отзыву:";
    public static final String SET_REVIEW_PLACE_TEXT ="Введите адрес проведения заведения:" + "\n" +
            "Вводить в формате: " + "*Город*, *Улица*, *Номер дома*";
    public static final String SET_REVIEW_EVENT_DATE_TEXT = "Введите дату посещения" + "\n" +
                    "Вводить в формате: ХХ.ХХ.ХХХХ ХХ:ХХ";
    public static final String SET_REVIEW_USER_LIMIT_TEXT = "//Введите максимальное количество участников события:";
    public static final String REVIEW_DATE_TEXT = "Дата некорректна. Попробуйте ещё раз";
    public static final String SET_REVIEW_CAFE_TYPE_TEXT = "Введите тип заведения:";
    public static final String SET_REVIEW_FORENAME_TEXT = "Введите название кафе:";
    public static final String REGISTER_REVIEW_END_TEXT = "Отзыв успешно создан";
    public static final String INCORRECT_REVIEW_TITLE = "Некорректно введен заголовок отзыва. Попробуйте ещё раз, используя буквы и/или эмоджи. Длина не должна превышать 100 символов";
    public static final String INCORRECT_REVIEW_DESCRIPTION = "Вы использовали недопустимые слова, или некорректно введели, или превысили максимальную длину в 1000 символов для комментария отзыва";
    public static final String INCORRECT_REVIEW_PLACE = "Некорректно введен адрес, посещенного кафе. Попробуйте ещё раз, используя буквы и/или эмоджи";


}
