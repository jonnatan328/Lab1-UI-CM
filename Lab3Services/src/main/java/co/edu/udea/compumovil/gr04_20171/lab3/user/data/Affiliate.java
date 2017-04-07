
package co.edu.udea.compumovil.gr04_20171.lab3.user.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Affiliate {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ttl")
    @Expose
    private Integer ttl;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("userId")
    @Expose
    private Integer userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
