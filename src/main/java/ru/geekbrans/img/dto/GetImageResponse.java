package ru.geekbrans.img.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

public class GetImageResponse extends CommonResponse<GetImageResponse.ImageData>{

    @Data
    public static class ImageData {

        @JsonProperty("id")
        private String id;
        @JsonProperty("title")
        private Object title;
        @JsonProperty("description")
        private Object description;
        @JsonProperty("datetime")
        private Integer datetime;
        @JsonProperty("type")
        private String type;
        @JsonProperty("animated")
        private Boolean animated;
        @JsonProperty("width")
        private Integer width;
        @JsonProperty("height")
        private Integer height;
        @JsonProperty("size")
        private Integer size;
        @JsonProperty("views")
        private Integer views;
        @JsonProperty("bandwidth")
        private Integer bandwidth;
        @JsonProperty("vote")
        private Object vote;
        @JsonProperty("favorite")
        private Boolean favorite;
        @JsonProperty("nsfw")
        private Boolean nsfw;
        @JsonProperty("section")
        private Object section;
        @JsonProperty("account_url")
        private Object accountUrl;
        @JsonProperty("account_id")
        private Integer accountId;
        @JsonProperty("is_ad")
        private Boolean isAd;
        @JsonProperty("in_most_viral")
        private Boolean inMostViral;
        @JsonProperty("has_sound")
        private Boolean hasSound;
        @JsonProperty("tags")
        private List<Object> tags = null;
        @JsonProperty("ad_type")
        private Integer adType;
        @JsonProperty("ad_url")
        private String adUrl;
        @JsonProperty("edited")
        private String edited;
        @JsonProperty("in_gallery")
        private Boolean inGallery;
        @JsonProperty("deletehash")
        private String deletehash;
        @JsonProperty("name")
        private Object name;
        @JsonProperty("link")
        private String link;
        @JsonProperty("ad_config")
        private AdConfig adConfig;

    }

    public static class AdConfig {

        @JsonProperty("safeFlags")
        private List<String> safeFlags = null;
        @JsonProperty("highRiskFlags")
        private List<Object> highRiskFlags = null;
        @JsonProperty("unsafeFlags")
        private List<String> unsafeFlags = null;
        @JsonProperty("wallUnsafeFlags")
        private List<Object> wallUnsafeFlags = null;
        @JsonProperty("showsAds")
        private Boolean showsAds;

    }

}
