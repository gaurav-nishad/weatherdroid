package cn.tearcry.android.gui;

import java.io.File;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.tearcry.api.weather.WeatherCondition;
import cn.tearcry.api.weather.WeatherKey;
import cn.tearcry.api.weather.WeatherParser;
import android.view.animation.LayoutAnimationController;

public class GUIDesign extends Activity {
	
	private TextView loc_name;
	private TextView temp;
	private TextView weather_desc;
	private TextView wind;
	private TextView humid;
	private TextView feels_like;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(50);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(100);
        set.addAnimation(animation);
        LinearLayout main_layout=(LinearLayout)findViewById(R.id.main_layout);
        LayoutAnimationController controller=new LayoutAnimationController(set,0.5f);
        main_layout.setLayoutAnimation(controller);
     
        
        
        
        ImageView image=(ImageView)findViewById(R.id.day_icon);  
        image.setImageResource(R.drawable.large32);
        temp=(TextView)findViewById(R.id.temp);
        weather_desc=(TextView)findViewById(R.id.weather_desc);
        wind=(TextView)findViewById(R.id.wind);
        feels_like=(TextView)findViewById(R.id.feels_like);
        humid=(TextView)findViewById(R.id.humid);

       
        loc_name=(TextView)findViewById(R.id.loc_name);
        File file=new File("/sdcard/test.xml");
        
        if(file.exists()) {
        	WeatherParser parser = new WeatherParser();
        	WeatherCondition wc=WeatherCondition.queryWeather(file, parser);
        	HashMap<String, String>data=wc.getCurrent().getDayCommonData();
        
        	loc_name.setText(data.get(WeatherKey.LOCATION_NAME));
        	temp.setText(getText(R.string.temp)+data.get(WeatherKey.TEMPERATURE)+data.get(WeatherKey.Unit.UNIT_TEMP));
        	weather_desc.setText(data.get(WeatherKey.TEXT_DESC));
        	
        	humid.setText(getText(R.string.humidity)+data.get(WeatherKey.HUMIDITY)+"%");
        	wind.setText(getText(R.string.wind)+data.get(WeatherKey.WIND_SPEED)+data.get(WeatherKey.Unit.UNIT_SPEED)
        			+getText(R.string.wind_direction)
        			+data.get(WeatherKey.WIND_DIRECTION));
        	feels_like.setText(getText(R.string.feels_like)+data.get(WeatherKey.FEELS_LIKE)+data.get(WeatherKey.Unit.UNIT_TEMP));
        	
        	
        }
       
       
    }
}