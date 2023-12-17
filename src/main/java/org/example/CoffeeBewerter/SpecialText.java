package org.example.CoffeeBewerter;

import com.vdurmont.emoji.EmojiParser;

import java.util.ArrayList;
import java.util.Arrays;

public class SpecialText {
    // Emoji
    public static final String SMILE_EMOJI = ":smiley:";
    public static final String CLIP_EMOJI = ":clipboard:";
    public static final String HOUSE_EMOJI = ":house:";
    // HELP_TEXT
    public static final String INDEV_TEXT = "Скоро я смогу понять, что ты написал";

    public static final String HELP_TEXT = EmojiParser.parseToUnicode("Этот бот помогает сохранять отзывы о различных кафе.\n" +
                    "Пройди регистрацию, выбрав в меню слева от ввода команду регистрации" + CLIP_EMOJI + "\nТак ты сможешь оставлять отзывы на кафе. \n" +
                    "Нажми на кнопку справа от ввода, чтобы выбрать команду");

    public static final String NO_CAFES_TEXT = EmojiParser.parseToUnicode("У вас пока нет посещенных кафе. Добавить новое кафе?" + HOUSE_EMOJI);
    public static final String DELETE_PROFILE_QUESTION_TEXT = "Вы уверены, что хотите удалить свой профиль?";

    public static final String PHOTO_TO_YOU = "Фото для Вас!";


    public static String generateStartReviewMessage(String name) {
        return EmojiParser.parseToUnicode("Привет, " + name + "!" + " " + SMILE_EMOJI);
    }

    public static String generateAdminBroadcastMessage(String msg) {
        return EmojiParser.parseToUnicode(SMILE_EMOJI + "В работе бота произошли изменения" + msg);
    }

    public static final String GLOBAL_CONTEXT_CAFE_DELETE = "global_context_cafe_delete";
    public static final String GLOBAL_CONTEXT_DEFAULT = "default";
    public static final String GLOBAL_CONTEXT_USER_REGISTRATION = "user_registration";
    public static final String GLOBAL_CONTEXT_CAFE_REGISTRATION = "cafe_registration";
    public static final String GLOBAL_CONTEXT_USER_EDIT = "user_edit";
    public static final String GLOBAL_CONTEXT_REVIEW_REGISTRATION = "review_registration";
    public static final String GLOBAL_ADMIN_BROADCAST = "global_admin_broadcast";
    public static final String GLOBAL_CONTEXT_FULL_REVIEW_INFO = "global_context_full_review_info";
    public static final String GLOBAL_CONTEXT_REVIEW_DELETE = "global_context_review_delete";
    public static final String GLOBAL_CONTEXT_REVIEW_EDIT = "global_context_review_edit";
    public static final String GLOBAL_CONTEXT_SET_NAME_FIELD_REVIEW = "global_context_set_name_field_review";

    public static final String GLOBAL_CONTEXT_REVIEW_SELECT_NUMBER_REVIEW = "global_context_review_select_number_review";

    // global files
    public static final String PHOTO_STORAGE_DIR = "downloaded_photos";
    public static final String CAFFE_STORAGE_DIR = "caffe_photos";
    public static final String CAFE_IMG_FILE_NAME = "cafe_image_";
    public static final String CAFE_IMG_FILE_EXTENSION = ".jpg";
    public static final String TG_DOWNLOAD_FILE_LINK = "https://api.telegram.org/file/bot";

    // admin command
    public static final ArrayList<Long> adminsIdList = new ArrayList<>(Arrays.asList(919433897L, 881861312L));
    public static final String ADMIN_INPUT_BROADCAST_MESSAGE = "Введите текст объявления, которое будет доступно ВСЕМ пользователям бота: ";
    public static final String NOT_AN_ADMIN = "Вам не разрешено выполнять данную команду";


}
