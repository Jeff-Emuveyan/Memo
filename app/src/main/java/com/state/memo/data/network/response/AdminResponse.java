
package com.state.memo.data.network.response;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;
import com.state.memo.model.Admin;
@SuppressWarnings("unused")

@Keep
public class AdminResponse {

    @SerializedName("admin")
    private Admin mAdmin;

    public Admin getAdmin() {
        return mAdmin;
    }

    public void setAdmin(Admin admin) {
        mAdmin = admin;
    }

}
