package br.com.hole19.marvel.comm.util;

import android.content.Context;
import android.net.Uri;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import br.com.hole19.marvel.comm.R;

/**
 * Created by edgar on 29-Apr-16.
 */
public class RequestUtil {

    public String buildBasicUrl(Context context, String service, String query, int limit, int offset) {
        Uri.Builder builder = new Uri.Builder();
        String timestamp = this.getTimestamp();
        builder.scheme("https")
                .authority(context.getString(R.string.MARVEL_API_BASE_URL))
                .appendPath(context.getString(R.string.MARVEL_API_VERSION))
                .appendPath(context.getString(R.string.MARVEL_API_VISIBILITY))
                .appendPath(service)
                .appendQueryParameter(context.getString(R.string.MARVEL_API_PARAM_TIMESTAMP), timestamp)
                .appendQueryParameter(context.getString(R.string.MARVEL_API_PARAM_API_KEY), context.getString(R.string.MARVEL_KEY_PUBLIC))
                .appendQueryParameter(context.getString(R.string.MARVEL_API_PARAM_API_HASH), this.getHash(context, timestamp))
                .appendQueryParameter(context.getString(R.string.MARVEL_API_PARAM_LIMIT_LABEL), new Integer(limit).toString())
                .appendQueryParameter(context.getString(R.string.MARVEL_API_PARAM_OFFSET_LABEL), new Integer(offset).toString());

        if (!"".equals(query)) {
            builder.appendQueryParameter(context.getString(R.string.MARVEL_API_PARAM_QUERY), query);
        }

        return builder.build().toString();
    }

    public String parseExistentRequest(String url, Context context) {
        String timestamp = this.getTimestamp();
        StringBuilder builder = new StringBuilder(url);
        builder.append("?");
        builder.append("ts=");
        builder.append(timestamp);
        builder.append("&");
        builder.append("apikey=");
        builder.append(context.getString(R.string.MARVEL_KEY_PUBLIC));
        builder.append("&");
        builder.append("hash=");
        builder.append(this.getHash(context, timestamp));
        return builder.toString();
    }

    public String getHash(Context context, String timestamp) {
        StringBuilder digest = new StringBuilder();
        digest.append(timestamp);
        digest.append(context.getString(R.string.MARVEL_KEY_PRIVATE));
        digest.append(context.getString(R.string.MARVEL_KEY_PUBLIC));
        return new String(Hex.encodeHex(DigestUtils.md5(digest.toString())));
    }

    public String getTimestamp() {
        Long timestamp = (System.currentTimeMillis() / 1000);
        return timestamp.toString();
    }

}
