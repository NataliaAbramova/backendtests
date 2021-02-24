package ru.geekbrans.img;

public enum Images {

    POSITIVE("src/test/resources/bfoto_ru_3269.jpg", 820, 579),
    FROM_URL("https://upload.wikimedia.org/wikipedia/commons/8/80/140-P1020281_-_Flickr_-_Laurie_Nature_Bee.jpg", 3504, 2336),
    TO_BIG("src/test/resources/DSC_0557.JPG", 0,0);

    public final String path;
    public final int width;
    public final int height;

    Images(String path, int width, int height) {
        this.path = path;
        this.width = width;
        this.height = height;
    }

}
