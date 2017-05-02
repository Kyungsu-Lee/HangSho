package com.example.lmasi.hangsho;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

/**
 * Created by lmasi on 2016-09-17.
 */
public class Activity_ChooseStore extends Activity {

    RelativeLayout main;
    IconLayout iconLayout;

    ImageView background;

    TouchImageView backBtn;

    TextView title;
    EditText searchBar;
    ImageView searchIcon;

    RelativeLayout textBox;
    TextView localName;
    ImageView list_arrow;

    HangShoScrollView hangShoScrollView;
    Vector<StoreCard> storeCards = new Vector<>();
    ArrayList<ShopInfo> infos = new ArrayList<>();

    ImageView checkBox;

    TextView sort_name;
    TextView sort_pop;
    ImageView sort_bar;
    ImageView sort_circle;

    RelativeLayout sortLayout;

    int shopImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosestore);

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.argb(90, 27, 27, 25));

        background = new ImageView(this);
        background.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x(), 353 * ScreenParameter.getScreenparam_y()));
        background.setBackground(getResources().getDrawable(R.drawable.grad));
        background.setId(background.hashCode());
        main.addView(background);

        backBtn = new TouchImageView(this);
        backBtn.setBackground(R.drawable.home_back);
        backBtn.setSize(80 * ScreenParameter.getScreenparam_x(), 80 * ScreenParameter.getScreenparam_y());
        backBtn.setLocation(32 * ScreenParameter.getScreenparam_x(), 18 * ScreenParameter.getScreenparam_y());
        backBtn.setId(backBtn.hashCode());
        main.addView(backBtn);
        backBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                startActivity(new Intent(Activity_ChooseStore.this, Activity_Local.class));
                finish();

                return false;
            }
        });

        title = new TextView(this);
        title.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.ALIGN_TOP, backBtn.getId()).setMargin(0, 20 * ScreenParameter.getScreenparam_y(), 0, 0));
        title.setText("매장선택");
        title.setTextColor(Color.argb(255, 255, 255, 255));
        title.setTypeface(FontResource.AppleSDGothicNeoB00);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        main.addView(title);

        textBox = new RelativeLayout(this);
        textBox.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.ALIGN_BOTTOM, background.getId()).setMargin(47 * ScreenParameter.getScreenparam_x(), 0 * ScreenParameter.getScreenparam_y(), 0, 34 * ScreenParameter.getScreenparam_y()));
        main.addView(textBox);
        textBox.setId(textBox.hashCode());

        localName = new TextView(this);
        localName.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_VERTICAL));
        localName.setText(DbResource.Location);
        localName.setTextColor(Color.argb(255, 255, 255, 255));
        localName.setTypeface(FontResource.AppleSDGothicNeoB00);
        localName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        localName.setId(localName.hashCode());
        textBox.addView(localName);

        if(DbResource.Location.equals("") || DbResource.Location == null)
            DbResource.Location = getIntent().getStringExtra("LocalName");

        list_arrow = new ImageView(this);
        list_arrow.setBackground(getResources().getDrawable(R.drawable.list_arrow));
        list_arrow.setLayoutParams(new HangShowLayoutParam(25 * ScreenParameter.getScreenparam_x(), 12 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.RIGHT_OF, localName.getId()).setMargin(10 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        textBox.addView(list_arrow);

        searchBar = new EditText(this);
        searchBar.setLayoutParams(new HangShowLayoutParam(681 * ScreenParameter.getScreenparam_x(), 64 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.ABOVE, textBox.getId()).setMargin(0,0 * ScreenParameter.getScreenparam_y(), 0, 60 * ScreenParameter.getScreenparam_y() ));
        searchBar.setBackground(getResources().getDrawable(R.drawable.list_searchbar));
        main.addView(searchBar);
        searchBar.setPadding((int)(30 * ScreenParameter.getScreenparam_x()), 0, 0, 0);
        //searchBar.setTextColor(Color.argb(255, 160, 159 ,159));
        searchBar.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        searchBar.setTypeface(FontResource.AppleSDGothicNeoB00);
        searchBar.setHint("매장 검색 (매장명)");
        searchBar.setHintTextColor(Color.argb(255, 160, 159, 159));
        searchBar.setId(searchBar.hashCode());
        searchBar.setSingleLine();

        searchIcon = new ImageView(this);
        searchIcon.setBackground(getResources().getDrawable(R.drawable.list_searchicon));
        searchIcon.setLayoutParams(new HangShowLayoutParam(39 * ScreenParameter.getScreenparam_x(), 39 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.ALIGN_RIGHT, searchBar.getId()).addRules(RelativeLayout.ALIGN_TOP, searchBar.getId()).setMargin(0, 13 * ScreenParameter.getScreenparam_y(), 30 * ScreenParameter.getScreenparam_x(), 0));
        main.addView(searchIcon);
        searchIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()  == MotionEvent.ACTION_UP)
                {
                    hangShoScrollView.removeAllView();
                    Collections.sort(storeCards, new CompareByIndex());

                    String keyword = searchBar.getText().toString();

                    for(int i=0 ;i<storeCards.size(); i++)
                    {
                        if(storeCards.get(i).getShopName().indexOf(keyword) > 0)
                            hangShoScrollView.addObject(storeCards.get(i));
                    }
                    hangShoScrollView.setScrollView();
                }

                return false;
            }
        });

        sortLayout = new RelativeLayout(getApplicationContext());
        sortLayout.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.ALIGN_RIGHT, searchBar.getId()).addRules(RelativeLayout.BELOW, searchBar.getId()).addRules(RelativeLayout.ALIGN_TOP, localName.getId()).setMargin(0, 5 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(sortLayout);

        sort_pop = new TextView(getApplicationContext());
        sort_pop.setText("인기 순");
        sort_pop.setTextColor(Color.argb(255, 255, 255, 255));
        sort_pop.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        sort_pop.setTypeface(FontResource.AppleSDGothicNeoB00);
        sort_pop.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.ALIGN_PARENT_RIGHT).addRules(RelativeLayout.CENTER_VERTICAL));
        sortLayout.addView(sort_pop);
        sort_pop.setId(sort_pop.hashCode());

        sort_bar = new ImageView(getApplicationContext());
        sort_bar.setBackground(getResources().getDrawable(R.drawable.list_bar));
        sort_bar.setLayoutParams(new HangShowLayoutParam(116 * ScreenParameter.getScreenparam_x(), 25 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.LEFT_OF, sort_pop.getId()).setMargin(0, 0, 15 * ScreenParameter.getScreenparam_x(), 0));
        sortLayout.addView(sort_bar);
        sort_bar.setId(sort_bar.hashCode());

        sort_name = new TextView(getApplicationContext());
        sort_name.setText("이름 순");
        sort_name.setTextColor(Color.argb(255, 255, 255, 255));
        sort_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        sort_name.setTypeface(FontResource.AppleSDGothicNeoB00);
        sort_name.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.LEFT_OF, sort_bar.getId()).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(0, 0, 15 * ScreenParameter.getScreenparam_x(), 0));
        sortLayout.addView(sort_name);

        sort_circle = new ImageView(getApplicationContext());
        sort_circle.setBackground(getResources().getDrawable(R.drawable.list_icon1));
        sort_circle.setLayoutParams(new HangShowLayoutParam(50 * ScreenParameter.getScreenparam_x(), 50 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.ALIGN_LEFT, sort_bar.getId()));
        sortLayout.addView(sort_circle);

        hangShoScrollView = new HangShoScrollView(this);
        hangShoScrollView.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 862 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, background.getId()));
        main.addView(hangShoScrollView);


        for(int i=0; i<Resources.STORENAME.length; i++)
        {
            final StoreCard storeCard = new StoreCard(this, Resources.STORENAME[i], Resources.STORENAME_SUB[i], i) {
                @Override
                public void clickEventShop() {
                    if(infos.size() <= 5) {

                        background.getLayoutParams().height = (int) (545 * ScreenParameter.getScreenparam_y());

                        if (iconLayout == null) {
                            iconLayout = new IconLayout(getContext());
                            iconLayout.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x(), 157 * ScreenParameter.getScreenparam_y()).setMargin(0, 155 * ScreenParameter.getScreenparam_y(), 0, 0));
                            iconLayout.setBackgroundColor(Color.argb(127, 255, 255, 255));
                            main.addView(iconLayout);

                            iconLayout.setScrollView();
                        }


                        infos.add(new ShopInfo("shop", getShopName(), getsubName()));
                        iconLayout.addObject(new LikeIcon(getContext(), "shop", getShopName()) {
                            @Override
                            public void clickCloseButton(View view) {
                                iconLayout.removeObject((LikeIcon) view);
                                shopUnClick();

                                if (iconLayout != null && iconLayout.getCount() == 0) {
                                    background.getLayoutParams().height = (int) (353 * ScreenParameter.getScreenparam_y());
                                    main.removeView(iconLayout);
                                    iconLayout = null;
                                }

                                for (int i = 0; i < infos.size(); i++)
                                    if (infos.get(i).equals(new ShopInfo("shop", getShopName(), getsubName())))
                                        infos.remove(infos.get(i));
                            }
                        });
                    }
                }

                @Override
                public void clickEventEyes() {
                    if(infos.size() <= 5) {
                        background.getLayoutParams().height = (int) (545 * ScreenParameter.getScreenparam_y());

                        if (iconLayout == null) {
                            iconLayout = new IconLayout(getContext());
                            iconLayout.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x(), 157 * ScreenParameter.getScreenparam_y()).setMargin(0, 155 * ScreenParameter.getScreenparam_y(), 0, 0));
                            iconLayout.setBackgroundColor(Color.argb(127, 255, 255, 255));
                            main.addView(iconLayout);

                            iconLayout.setScrollView();
                        }

                        infos.add(new ShopInfo("eye", getShopName(), getsubName()));
                        iconLayout.addObject(new LikeIcon(getContext(), "eye", getShopName()) {
                            @Override
                            public void clickCloseButton(View view) {

                                iconLayout.removeObject((LikeIcon) view);
                                eyeUnClick();

                                if (iconLayout != null && iconLayout.getCount() == 0) {
                                    background.getLayoutParams().height = (int) (353 * ScreenParameter.getScreenparam_y());
                                    main.removeView(iconLayout);
                                    iconLayout = null;
                                }

                                for (int i = 0; i < infos.size(); i++)
                                    if (infos.get(i).equals(new ShopInfo("eye", getShopName(), getsubName())))
                                        infos.remove(infos.get(i));
                            }
                        });
                    }
                }

                @Override
                public void unclickEventShop() {


                    iconLayout.removeObject(getShopName(), "shop");

                    if(iconLayout != null && iconLayout.getCount() == 0)
                    {
                        background.getLayoutParams().height = (int)(353 * ScreenParameter.getScreenparam_y());
                        main.removeView(iconLayout);
                        iconLayout = null;
                    }

                    for(int i=0; i<infos.size(); i++)
                        if(infos.get(i).equals(new ShopInfo("shop", getShopName(), getsubName())))
                            infos.remove(infos.get(i));
                }

                @Override
                public void unclickEventEyes() {


                    iconLayout.removeObject(getShopName(), "eye");

                    if(iconLayout != null && iconLayout.getCount() == 0)
                    {
                        background.getLayoutParams().height = (int)(353 * ScreenParameter.getScreenparam_y());
                        main.removeView(iconLayout);
                        iconLayout = null;
                    }

                    for(int i=0; i<infos.size(); i++)
                        if(infos.get(i).equals(new ShopInfo("eye", getShopName(), getsubName())))
                            infos.remove(infos.get(i));
                }

                @Override
                public void clickCard() {

                    ShopImageView shopImageView = new ShopImageView(getContext(), getIndex());
                    shopImage = shopImageView;
                    addView(shopImageView);
                   // shopImageView.addRule(RelativeLayout.ALIGN_BASELINE, getId());
                    shopImageView.addRule(RelativeLayout.BELOW, getMain().getId());
                    getLayoutParams().height = LayoutParams.WRAP_CONTENT;

                }

                @Override
                public void unclickCard() {

                    removeView(shopImage);
                    getLayoutParams().height = (int)(96 * ScreenParameter.getScreenparam_y());

                }
            };
            storeCard.setId(storeCard.hashCode());
            storeCards.add(storeCard);
        }


        Collections.sort(storeCards, new CompareByName());
        //Collections.sort(storeCards, new CompareByIndex());


        for(int i=0 ;i<storeCards.size(); i++)
        {
            if(i%4 == 0)
                storeCards.get(i).Like();

            storeCards.get(i).setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 96 * ScreenParameter.getScreenparam_y()).setMargin(0, 2 * ScreenParameter.getScreenparam_y(), 0, 0));
            hangShoScrollView.addObject(storeCards.get(i));

        }


        hangShoScrollView.setScrollView();




        checkBox = new ImageView(this);
        checkBox.setBackground(getResources().getDrawable(R.drawable.list_checkbutton));
        checkBox.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 120 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.ALIGN_PARENT_BOTTOM));
        main.addView(checkBox);
        checkBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(!DbResource.iscalled)
                    {
                        SocketService.sendmessage("CALLSHOP " + DbResource.Location);
                        DbResource.iscalled = false;
                    }
                    startActivity(new Intent(Activity_ChooseStore.this, Activity_shopList.class).putExtra("LIST", infos));
                    finish();
                }

                return true;
            }
        });

        View.OnTouchListener touchListener = new View.OnTouchListener() {

            boolean name = false;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(name)
                    {
                        sort_circle.setLayoutParams(new HangShowLayoutParam(50 * ScreenParameter.getScreenparam_x(), 50 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.ALIGN_LEFT, sort_bar.getId()));
                        sortName();
                    }
                    else
                    {
                        sort_circle.setLayoutParams(new HangShowLayoutParam(50 * ScreenParameter.getScreenparam_x(), 50 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.ALIGN_RIGHT, sort_bar.getId()).setMargin(0, 0, -5 * ScreenParameter.getScreenparam_x() ,0));
                        sortPop();
                    }

                    name = !name;
                }

                return true;
            }
        };
        sort_circle.setOnTouchListener(touchListener);
        sort_bar.setOnTouchListener(touchListener);

        main.setFocusable(true);
        main.setFocusableInTouchMode(true);
    }

    static class CompareByName implements Comparator<StoreCard>
    {

        @Override
        public int compare(StoreCard storeCard, StoreCard other) {

            return storeCard.getShopName().compareTo(other.getShopName());
        }
    }

    static class CompareByIndex implements Comparator<StoreCard>
    {

        @Override
        public int compare(StoreCard storeCard, StoreCard other) {

            return Integer.compare(storeCard.index, other.index);
        }
    }

    public void sortName()
    {
        hangShoScrollView.removeAllView();
        Collections.sort(storeCards, new CompareByName());

        for(int i=0 ;i<storeCards.size(); i++)
        {
           // storeCards.get(i).setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 96 * ScreenParameter.getScreenparam_y()).setMargin(0, 2 * ScreenParameter.getScreenparam_y(), 0, 0));
            hangShoScrollView.addObject(storeCards.get(i));
        }
        hangShoScrollView.setScrollView();
    }

    public void sortPop()
    {
        hangShoScrollView.removeAllView();
        Collections.sort(storeCards, new CompareByIndex());

        for(int i=0 ;i<storeCards.size(); i++)
        {
           // storeCards.get(i).setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 96 * ScreenParameter.getScreenparam_y()).setMargin(0, 2 * ScreenParameter.getScreenparam_y(), 0, 0));
            hangShoScrollView.addObject(storeCards.get(i));
        }
        hangShoScrollView.setScrollView();
    }
}

