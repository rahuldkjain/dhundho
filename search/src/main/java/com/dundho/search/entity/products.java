package com.dundho.search.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true)
@SolrDocument(solrCoreName = "users")
public class products {
    public static final String USER_SOLR_FILED_ID = "id";
    public static final String USER_SOLR_FIELD_BOT_REF = "bot_ref";
    public static final String USER_SOLR_FIELD_USER_ID = "user_id";
    public static final String USER_SOLR_FIELD_FIRST_NAME = "first_name";
    public static final String USER_SOLR_FIELD_LAST_NAME = "last_name";
    public static final String USER_SOLR_FIELD_EMAIL = "email";
    public static final String USER_SOLR_FIELD_PROFILE_PIC_URL = "profile_pic_url";
    public static final String USER_SOLR_FIELD_GENDER = "gender";
    public static final String USER_SOLR_FIELD_LOCALE = "locale";
    public static final String USER_SOLR_FIELD_TIMEZONE = "timezone";
    public static final String USER_SOLR_FIELD_PLATFORM = "platform";
    public static final String USER_SOLR_FIELD_CREATED_AT = "created_at";
    public static final String USER_SOLR_FIELD_LAST_ACTIVE_AT = "last_active_at";
    public static final String USER_SOLR_FIELD_SUBSCRIBED = "subscribed";
    public static final String USER_SOLR_FIELD_IS_ENABLE = "is_enable";
    public static final String USER_SOLR_FIELD_USERNAME = "username";
    @Id
    @Indexed(name = USER_SOLR_FILED_ID, type = "string", required = true)
    private String id;
    @Indexed(name = USER_SOLR_FIELD_BOT_REF, type = "long", required = true)
    private Long botRef;
    @Indexed(name = USER_SOLR_FIELD_USER_ID, type = "string", required = true)
    private String userId;
    @Indexed(name = USER_SOLR_FIELD_FIRST_NAME, type = "string", required = true, stored = false)
    private String firstName;
    @Indexed(name = USER_SOLR_FIELD_LAST_NAME, type = "string", required = true, stored = false)
    private String lastName;
    @Indexed(name = USER_SOLR_FIELD_EMAIL, type = "string", required = true, stored = false)
    private String email;
    @Indexed(name = USER_SOLR_FIELD_PROFILE_PIC_URL, type = "string", required = true, stored = false)
    private String profilePicUrl;
    @Indexed(name = USER_SOLR_FIELD_GENDER, type = "string", required = true, stored = false)
    private String gender;
    @Indexed(name = USER_SOLR_FIELD_LOCALE, type = "string", required = true, stored = false)
    private String locale;
    @Indexed(name = USER_SOLR_FIELD_TIMEZONE, type = "string", required = true, stored = false)
    private String timezone;
    @Indexed(name = USER_SOLR_FIELD_CREATED_AT, type = "string", required = true, stored = false)
    private Date createdAt;
    @Indexed(name = USER_SOLR_FIELD_LAST_ACTIVE_AT, type = "string", required = true, stored = false)
    private Date lastActiveAt;
    @Indexed(name = USER_SOLR_FIELD_SUBSCRIBED, type = "string", required = true, stored = false)
    private Boolean subscribed;
    @Indexed(name = USER_SOLR_FIELD_IS_ENABLE, type = "string", required = true, stored = false)
    private Boolean isEnabled;
    @Indexed(name = USER_SOLR_FIELD_USERNAME, type = "string", required = true, stored = false)
    private String userName;
    @Dynamic
    @Indexed(name = "attributes_text_*", type = "string", required = true, stored = false)
    private Map<String, String> textAttributes;
    @Dynamic
    @Indexed(name = "attributes_date_*", type = "string", required = true, stored = false)
    private Map<String, String> dateAttributes;
    @Dynamic
    @Indexed(name = "attributes_number_*", type = "string", required = true, stored = false)
    private Map<String, String> numberAttributes;
}
