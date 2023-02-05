package edu.northeastern.numad23sp_shambhavikul;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


public class LinkCard extends AppCompatActivity {

    private String linkName;
    private String linkUrl;

    public LinkCard() {}
    public LinkCard(String linkName, String linkUrl) {
        this.linkName = linkName;
        this.linkUrl = linkUrl;
    }

    public String getLinkName() {
        return  linkName;
    }

    public String getLinkUrl() {
        return  linkUrl;
    }

    public void onLinkCardClicked(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl));
        context.startActivity(intent);
    }

    public boolean isCorrect() {
        try {
            new URL(linkUrl).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return Patterns.WEB_URL.matcher(linkUrl).matches();
    }
}