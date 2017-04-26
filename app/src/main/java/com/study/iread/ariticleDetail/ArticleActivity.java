package com.study.iread.ariticleDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.study.iread.R;

/**
 * Created by dnw on 2017/4/26.
 */
public class ArticleActivity extends AppCompatActivity {
    private ArticleFragment articleFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity_layout);
        if(savedInstanceState!=null)
        {
            articleFragment=(ArticleFragment) getSupportFragmentManager().getFragment(savedInstanceState,"articleFragment");
        }else
        {
            articleFragment=new ArticleFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.article_activity_layout,articleFragment)
                    .commit();
        }
        Intent intent = getIntent();

        ArticlePresenter presenter = new ArticlePresenter(ArticleActivity.this,articleFragment);

        presenter.setType(intent.getStringExtra("type"));
        presenter.setId(intent.getIntExtra("id", 0));
        presenter.setTitle(intent.getStringExtra("title"));
        presenter.setCoverUrl(intent.getStringExtra("coverUrl"));
    }
}
