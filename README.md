# Test_Singleton
Using Singleton in Android
## I. Introduction
As you known, in Android application, to send data from one Activity to another, you must use Intent object and putExtra() to it.
For example:
```java
Intent intent = new Intent(this, ScreenB. class);
intent.putExtra("user_id",userID);
startActivity(intent);
```
It have some problems.
* The first, ScreenB must known what **key** and **type of data** which was sent from screenA.
* The second, if you want to send a custom object, you must implement it from **Parcelrable**.
* The last, if screenB use the intent with wrong type of data, the compiler cannot recognize, it'll raise the **RuntimeException**. Not good at all !

## II. Solution
Okay, every Android developers know these problems. I will show you my solution now.

#### Step 1: Using dagger and dagger compiler
Add following line into your build.gradle file.

```
dependencies {
.......
compile 'com.squareup.dagger:dagger:1.2.2'
compile 'com.squareup.dagger:dagger-compiler:1.2.2'
}
```
#### Step 2: Add ```AppModule``` class and build ```GraphObject``` in your application class
**Please keep focus in ```annotations```, they are the key of this solution.**

```java
@Module(
        injects = {
                MainActivity.class,
                MainActivity2.class,
        }
)
public class AppModule {
}
```

```java
public class DemoApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new AppModule());
    }

    /**
     * inject an object
     * @param context
     * @param obj
     */
    public static void inject(Context context, Object obj){
        ((DemoApplication) context.getApplicationContext()).objectGraph.inject(obj);
    }
}
```

#### Step 3: Build the Singeton class

```java
@Singleton
public class SingletonData {

    private String savedText;

    @Inject
    public SingletonData(){}

    public String getSavedText() {
        return savedText;
    }

    public void setSavedText(String savedText) {
        this.savedText = savedText;
    }
}
```

#### Last step, using Singleton class to save and retrieve data.
In activity 1,
```java
@Inject
SingletonData data;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //inject this class 
    DemoApplication.inject(this,this);
    final EditText editText = (EditText) findViewById(R.id.editText);
    findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            data.setSavedText(editText.getText().toString());
            startActivity(new Intent(MainActivity.this,MainActivity2.class));
        }
    });
}
```

and in activity 2,

```java
@Inject
SingletonData data;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DemoApplication.inject(this,this);
    setContentView(R.layout.activity_main2);
    TextView tv = (TextView) findViewById(R.id.textView);
    tv.setText(data.getSavedText());
}
```
## III. Conclusion
As you see, this solution is very simple to implement in your project. And it's very effective.

The key of this solution is [dagger](https://github.com/square/dagger). I love [dagger](https://github.com/square/dagger).

It's the dependency injector framework built for Android. 

You can read more about dagger here https://github.com/square/dagger.

If you have any questions or idea, please give me an issue.

You are welcome !

Thank you.
