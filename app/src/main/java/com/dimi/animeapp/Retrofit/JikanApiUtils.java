package com.dimi.animeapp.Retrofit;

public class JikanApiUtils {

    public static String[] names = new String[]{
            "Action",
            "Adventure",
            "Cars", "Comedy", "Dementia",
            "Demons", "Mystery", "Drama", "Ecchi", "Fantasy",
            "Game", "Hentai", "Historical", "Horror", "Kids",
            "Magic", "Martial Arts", "Mecha", "Music", "Parody",
            "Samurai", "Romance", "School", "Sci-FI", "Shoujo",
            "Shoujo Ai", "Shounen", "Shounen Ai", "Space", "Sports",
            "Super Power", "Vampire", "Yaoi", "Yuri", "Harem", "Slice of Life",
            "SuperNatural", "Military", "Police", "Psychological", "Thriller",
            "Seinen", "Josei"
    };

    public static final int GENRE_ACTION = 1;
    public static final int GENRE_ADVENTURE = 2;
    public static final int GENRE_CARS = 3;
    public static final int GENRE_COMEDY = 4;
    public static final int GENRE_DEMENTIA = 5;
    public static final int GENRE_DEMONS = 6;
    public static final int GENRE_MYSTERY = 7;
    public static final int GENRE_DRAMA = 8;
    public static final int GENRE_ECCHI = 9;
    public static final int GENRE_FANTASY = 10;
    public static final int GENRE_GAME = 11;
    public static final int GENRE_HENTAI = 12;
    public static final int GENRE_HISTORICAL = 13;
    public static final int GENRE_HORROR = 14;
    public static final int GENRE_KIDS = 15;
    public static final int GENRE_MAGIC = 16;
    public static final int GENRE_MARTIAL_ARTS = 17;
    public static final int GENRE_MECHA = 18;
    public static final int GENRE_MUSIC = 19;
    public static final int GENRE_PARODY = 20;
    public static final int GENRE_SAMURAI = 21;
    public static final int GENRE_ROMANCE = 22;
    public static final int GENRE_SCHOOL = 23;
    public static final int GENRE_SCI_FI = 24;
    public static final int GENRE_SHOUJO = 25;
    public static final int GENRE_SHOUJO_AI = 26;
    public static final int GENRE_SHOUNEN = 27;
    public static final int GENRE_SHOUNEN_AI = 28;
    public static final int GENRE_SPACE = 29;
    public static final int GENRE_SPORTS = 30;
    public static final int GENRE_SUPER_POWER = 31;
    public static final int GENRE_VAMPIRE = 32;
    public static final int GENRE_YAOI = 33;
    public static final int GENRE_YURI = 34;
    public static final int GENRE_HAREM = 35;
    public static final int GENRE_SLICE_OF_LIFE = 36;
    public static final int GENRE_SUPERNATURAL = 37;
    public static final int GENRE_MILITARY = 38;
    public static final int GENRE_POLICE = 39;
    public static final int GENRE_PSYCHOLOGICAL = 40;
    public static final int GENRE_THRILLER = 41;
    public static final int GENRE_SEINEN = 42;
    public static final int GENRE_JOSEI = 43;

    public static String formatVideoUrl( String trailer_url ) {
        return trailer_url.split("\\?")[0];
    }
}
