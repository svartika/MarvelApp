# MarvelApp ![Android CI](https://github.com/svartika/MarvelApp/workflows/Android%20CI/badge.svg)

## Introduction

I am trying to make a simple application to view Marvel Characters and their details.

The technologies that I am using here are -> **RxJava2, Retrofit, Hilt, MVC Architecture, LiveData, Data binding, Glide, AsyncDiffer**
This is a project in progress and I will be updating the newer things as and when I use them.

The first screen(CharactersListActivity) fetches the list of all characters and displays it in a recycler view.

The second screen(CharacterDetailsActivity) shows the details of a character.

## What's the best way to fetch Marvel Characters?
The most reliable way currently in android to make network calls is via retrofit.
And the best way to parse response is by using Rx Java. 

**Gradle Dependencies**
I integrated the following libraries
**RxJava2** ->
 - implementation 'io.reactivex.rxjava2:rxjava:2.2.8'
 - implementation 'io.reactivex.rxjava2:rxandroid:2.1.1' 	
   Note: I did not use RxJava3 as its adapter for retrofit was still meant for
   RxJava2. 	
 - implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'
 - implementation 'com.squareup.okhttp3:logging-interceptor:4.2.1'
 
**Retrofit** ->
 - implementation 'com.google.code.gson:gson:2.8.5'
 - implementation 'com.squareup.retrofit2:retrofit:2.4.0'
 - implementation 'com.squareup.retrofit2:converter-gson:2.4.0'

I created MarvelRetrofitEndpointApi endpoint interface  as follows. This defines the api and the query and path parameters for all network calls.

    public interface MarvelRetrofitEndpointApi {
    		@GET("/v1/public/characters")
    		Observable<MarvelCharactersLoadResponse> loadCharacters();
    	}
    	
loadMarvelCharacters is defined in the network implementation class as follows
	
    public Observable<List<MarvelCharacter>> loadMarvelCharacters() {
            logger.d("Indivar", "Loading marvel characters");
            Observable<List<MarvelCharacter>> call = marvelRetrofitEndpointApi.loadCharacters().map(
                    marvelCharactersLoadResponse -> {
                        return marvelCharactersLoadResponse.data.characters;
                    }
            );
            return call;
    }
    
My first screen simply used Retrofit to get the list of marvel characters and RxJava to parse these results.

    void loadCharacters() {
                Observable<List<MarvelCharacter>> marvelCharacters = charactersListNetworkInterface.loadMarvelCharacters();
                Disposable disposable = marvelCharacters
                        .subscribeOn(Schedulers.io())  //create a background worker thread on which observable will carry out its task
                        .observeOn(AndroidSchedulers.mainThread()) //get hold of the main thread so that results can be sent back to the UI 
                        .subscribe(marvelCharactersList -> {
                                    Log.d("VartikaHilt", "Marvel Characters: " + marvelCharactersList.size());
                                    displayMarvelCharacters(marvelCharactersList);
                                },
                                err -> Log.d("VartikaHilt", err.getLocalizedMessage()),
                                () -> Log.d("VartikaHilt", "OnCompleted")
		    );
      }
	
## How to make my code more readable and maintainable?

I worked on separating the files in to different modules in order to follow the principles of **Clean Architecture** and **Model View Controller**.
I made the following modules 
 - App -> this module is aware of all the components in my project.
 - UI -> this contains the View components.
 - Controller -> this contains the business logic. I only included contract for the client here in form of interfaces.
 - ControllerImplementation -> this contains the implementation files for the contract that the controller made with the client. As of now it is only the network call related implementation (Retrofit)
 - Entity -> this contains the Model components. This module is expected to change only when we make fundamental changes to the application. 

This organization helped me to separate the project in to clear roles and responsibility. And since all the modules were not changed every time I made changes, it reduced the build time.
	
![Modules to implement MVC](https://github.com/svartika/MarvelApp/blob/master/documents/MVC.jpg?raw=true)

## How to make my code more stable and reusable?

Using dependency injection makes our code more decoupled. It moves a lot of boiler plate code away from the logic in to injection classes. The to go library for this in Android is Dagger 2. I started to integrate Dagger2 for dependency injection in my project. However, Android developer website suggested that I use Hilt. The documentation for Hilt is well written. I do not know Dagger2 but I was able to use Hilt with out many issues. 
I created my Logger class and used constructor and property based dependency injection for it.
I moved the logic for creation of retrofit so that it was created using method based dependency injection.
 1.  For hilt I included the following libraries 
 - apply plugin: 'kotlin-android'
 - apply plugin: 'kotlin-kapt'
 - apply plugin: 'dagger.hilt.android.plugin'
 - implementation "com.google.dagger:hilt-android:$hilt_version"
 - kapt "com.google.dagger:hilt-android-compiler:$hilt_version"
 2. In my Application class I gave the annotation @HiltAndroidApp
    @HiltAndroidApp
    	public class MarvelApplication extends Application {}
3. I added the Logger class in entity and marked its constructer with 
		
	    annotation @Inject
	    public class Logger {
    		@Inject
    		public Logger() { 
    		}
    		public void d(String tag, String message) {
    			Log.d(tag, message);
    		}
    	}
	
4. Usage of constructor based dependency injection
	
		@Inject
	    Logger logger;
	
5. In order to try out property based dependency injection, I used it as follows in the NetworkImpementationController class

		Logger logger; //declaration
		@Inject
	    public CharactersListNetworkInterfaceImpl(Logger logger) { //passed as a parameter in the constructer
	        this.logger = logger; 
	    }
6. Now, I moved to porting the retrofit instantiation to Hilt. 
	
	For this, I created a Hilt Module with Binds for creating my network controller 
	
		@Module
		@InstallIn(ApplicationComponent.class) // inject the binding in Application class -> this is scoping (keeping the instance alive) 
		public abstract class InjectionModule {
			@Binds
			public abstract CharactersListNetworkInterface createRetrofit(
				CharactersListNetworkInterfaceImpl retrofitImpl
			);
		}
	
	I use this in the CharactersListActivity as follows
	
	    @Inject
	    CharactersListNetworkInterface charactersListNetworkInterface; // declaration
	    ...
	    Observable<List<MarvelCharacter>> marvelCharacters = charactersListNetworkInterface.loadMarvelCharacters(); //usage
	
	The loadMarvelCharacters() is implemented in class CharactersListNetworkInterfaceImpl 
	
	    public class CharactersListNetworkInterfaceImpl implements CharactersListNetworkInterface {
	    		@Inject
	    		MarvelRetrofitEndpointApi marvelRetrofitEndpointApi;
	    		...
	    		public Observable<List<MarvelCharacter>> loadMarvelCharacters() {
	    			Observable<List<MarvelCharacter>> call = marvelRetrofitEndpointApi.loadCharacters()
								    		  .map( marvelCharactersLoadResponse -> {
						    						return marvelCharactersLoadResponse.data.characters;
			    	});
	    			return call;
	    		}
	    	}
	
	I then created MarvelRetrofitEndpointApi marvelRetrofitEndpointApi. I had to use the @Provides annotation because the instance of EndpointApi had to be created using builder pattern. The function body in the annotated function tells Hilt how to provide an instance of the corresponding type. Hilt executes the function body every time it needs to provide an instance of that type.
	
		@Module
		@InstallIn(ApplicationComponent.class)
		public class InjectionModule2 {
			@Provides
			public MarvelRetrofitEndpointApi getEndPoint(@Marvel Retrofit retrofit {
				return retrofit.create(MarvelRetrofitEndpointApi.class);
			}
			@Marvel// custom qualifier for creating different instances of Retrofit 
				   //(in case my url changes or new query interceptors need to be used)
			@Provides
			public Retrofit bindRetrofit() {
				Gson gson = new GsonBuilder().setLenient().create();
				RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
				HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
				loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
				OkHttpClient okHttpClient = new OkHttpClient.Builder()
					.addInterceptor(new QueryInterceptor())
					.addInterceptor(loggingInterceptor)
					.build();
				return new Retrofit.Builder()
					.baseUrl(BASE_URL)
					.client(okHttpClient)
					.addConverterFactory(GsonConverterFactory.create(gson))
					.addCallAdapterFactory(rxAdapter)
					.build();
		}
	}
	
	I created the qualifier in an interface
		
	    @Qualifier
    	@Retention(RetentionPolicy.RUNTIME)
    	public @interface Marvel {}

	This means that if Retrofit bindRetrofit() has multiple bindings, I can use the annotation @Marvel to pick the binding I need

7. My first screen is quite ok now.
	I used the above mentioned libraries in my second screen as well.

## How to conform to MVC?
To confirm to MVC, I want to move all the data processing and decision making to controller modules. The View should just observe the controller and update itself. 
I started with applying this idea to the second screen (the detail activity)
I fetch marvel character detail in a State element and then use Livedata to notify UI component. The UI component in turn will use data binding to update itself.
In class CharacterDetailPageController, 
 - I created a class State to hold information about the Character,
   error and/or progress. I created MutableLiveData of type State. 
 - I provided a getter for this LiveData, so the activity can access it. 
 - I then changed my retrofit to post this LiveData once RxJava2
   Observable completes its subscribe.

		

		    public class CharacterDetailPageController {
    			MutableLiveData<State> marvelCharacterLiveData = new MutableLiveData<>();
    			...
    			public void loadCharacterDetails(String characterID) {
    				marvelCharacterLiveData.postValue(new State(true, false, null));
    				Observable<ProcessedMarvelCharacter> marvelCharacterObservable = characterDetailNetworkInterface.loadCharacterDetail(characterID);
    				marvelCharacterObservable
    						.subscribeOn(Schedulers.io())
    						.observeOn(AndroidSchedulers.mainThread())
    						.subscribe(
    								processedMarvelCharacter -> {
    									marvelCharacterLiveData.postValue(new State(false, false, processedMarvelCharacter));
    								},
    								err -> {
    									marvelCharacterLiveData.postValue(new State(false, true, null));
    									logger.d("VartikaHilt", err.getLocalizedMessage());
    								},
    								() -> {
    
    								});
    			}
    			public LiveData<State> getCharacterDetailLiveData() {
	    			return marvelCharacterLiveData;
    			}
    
    			public static class State {
    				public boolean loading;
    				public boolean error;
    				public ProcessedMarvelCharacter character;
    
    				public State(boolean loading, boolean error, ProcessedMarvelCharacter character) {
    					this.loading = loading;
    					this.error = error;
    					this.character = character;
    				}
    			}
    		}

In CharacterDetailsActivity,
	

 - I created a DataBinding for this activity
 - I observed on the LiveData which I had created earlier in the CharacterDetailPageController. 
 - Once I received the State variable, I simply passed this value to the binding which set this variable in the layout xml

	    public class CharacterDetailsActivity extends AppCompatActivity {	
    		@Inject
    		CharacterDetailPageController controller;
    		ActivityCharacterDetailBinding binding;
    		...
    		@Override
    		protected void onCreate(Bundle savedInstanceState) {
    			binding = DataBindingUtil.setContentView(this, R.layout.activity_character_detail);
    			...
    			controller.getCharacterDetailLiveData().observe(this,
                    (state -> {
                        setState(state);
    
    					}
                    ));
    			...
    		}
    		void setState(CharacterDetailPageController.State state) {
    			binding.setState(state);
    		}
    	}

In the activity_character_detail.xml for this activity, 
 - I have added a data element with the variable element.  	
 - I have received the State variable here and passed it on to the content
   layout.

		...
    	<data>
            <variable
                name="state"
                type="com.example.controllers.retrofit.CharacterDetailPageController.State" />
        </data>
    	...
    	<include layout="@layout/content_character_detail"
            app:contentstate="@{state}"/>
		
In content_character_detail.xml, 

 - I have used this variable to set the name and load image for the marvel character using Glide. 
 - Setting name was just a matter of using the name property in the ProcessedMarvelCharacter. Setting image required me to use Glide. 
	
	    ...
    	<data>
            <import type="android.view.View"/>
            <variable
                name="contentstate"
                type="com.example.controllers.retrofit.CharacterDetailPageController.State" />
        </data>
    	<TextView
            android:id="@+id/name"
            android:text="@{contentstate.character.name}"
    		...
    	/>
    	<ImageView
            android:id="@+id/image"
    		app:url="@{contentstate.character.imageurl}"
    		...
    	/>
    	...

	
You can see above that I had created an element called url in the ImageView.
I now needed to create a static function with annotation @BindingAdapter(url) to receive the url value and load it in ImageView using Glide.

I included the following libraries for Glide
 - implementation 'com.github.bumptech.glide:glide:4.11.0'
 - kapt 'com.github.bumptech.glide:compiler:4.11.0'
  																																																																																																																																																																I created a class for receiving all binding information for images
	
	    public class BindingUtils {
    		@BindingAdapter("url")
    		public static void loadImage(ImageView imageView, String url) {
    			new Logger().d("VartikaHilt", "load image: " + url);
    			Glide.with(imageView.getContext())
    					.load(url)
    					.apply(new RequestOptions().circleCrop())
    					.into(imageView);
    		}
    	}
    	
I applied the same flow in the List screen (first screen) also now. The twist here is that the data should be passed to the adapter of the recycler view.

Initial set up up is the same as the detail screen. 

I created CharactersListPageController to hold the instance of CharactersListNetworkInterface. This loads the marvel characters using retrofit and pass these to the processed marvel characters list in the State variable. The State is held in a MutableLiveData which is observed by the activity.

	    public class CharactersListPageController {
    		MutableLiveData<State> charactersLiveData = new MutableLiveData<State>();
    		@Inject
    		CharactersListNetworkInterface charactersListNetworkInterface;
    		@Inject
    		CharactersListPageController() { }
    
    		public void loadCharacters() {
    			charactersLiveData.postValue(new State(true, false, null));
    			Observable<List<ProcessedMarvelCharacter>> marvelCharacters = charactersListNetworkInterface.loadMarvelCharacters();
    			Disposable disposable = marvelCharacters
    					.subscribeOn(Schedulers.io()) 
    					.observeOn(AndroidSchedulers.mainThread()) 
    					.subscribe(marvelCharactersList -> {
    								charactersLiveData.postValue(new State(false, false, marvelCharactersList));
    							},
    							err -> {
    								charactersLiveData.postValue(new State(false, true, null));
    							});
    		}
    
    		public LiveData<State> getCharactersLiveData() {
    			return charactersLiveData;
    		}
    
    		public static class State {
    			public boolean loading;
    			public boolean error;
    			public List<ProcessedMarvelCharacter> marvelCharactersList;
    
    			public State(boolean loading, boolean error, List<ProcessedMarvelCharacter> marvelCharactersList) {
    				this.loading = loading;
    				this.error = error;
    				this.marvelCharactersList = marvelCharactersList;
    			}
    		}
    	}
    	
When the state is updated. The activity sets this in the layout using binding. At this point the list is provided in the recycler view. 
	
    public class CharactersListActivity extends AppCompatActivity {
    		...
    		ActivityMainBinding binding;
    		protected void onCreate(Bundle savedInstanceState) {
    			...
    			binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    			...
    			controller.getCharactersLiveData().observe(this, state -> { setState (state); });
    			controller.loadCharacters();
    			...
    		}
    		private void setState(CharactersListPageController.State state) {
    			binding.setState(state);
    		}
    	}
	
In activity_main.xml
	
	   <layout xmlns:android="http://schemas.android.com/apk/res/android"
		       xmlns:app="http://schemas.android.com/apk/res-auto"
		       xmlns:tools="http://schemas.android.com/tools">			
    		<data>
    			<variable
    				name="state"
					type="com.example.controllers.retrofit.CharactersListPageController.State"/>
    		</data>
    		...
    		<androidx.recyclerview.widget.RecyclerView
    			...
    			app:datasource="@{state.marvelCharactersList}"/>
    	</layout>

In BindingUtils, we define the BindingAdapter for datasource and set the adapter of the recyclerview.
	

    @BindingAdapter("datasource")
        public static void loadDataSource(RecyclerView rvMarvelCharacters, List<ProcessedMarvelCharacter>marvelCharactersList) {
            MarvelCharacterListAdapter marvelCharacterListAdapter = new MarvelCharacterListAdapter();
            rvMarvelCharacters.setAdapter(marvelCharacterListAdapter);
            marvelCharacterListAdapter.submitList(marvelCharactersList); // using submitList instead of notify data set changed  was a whole new topic for me. I will cover it next :)
        }
	
Next, we need to pass this to the individual elements in the recycler view.  In the item layout, marvel_character_rv_item.xml, i just added the data element and used it to populate the items.
	

    <layout>
    		...
    		<data>
    			<variable
    				name="marvelItem"
    				type="com.example.controllers.retrofit.ProcessedMarvelCharacter" />
    		</data>
    		...
    		<ImageView
    			android:id="@+id/mCharacterImage"
    			...
    			app:url="@{marvelItem.imageurl}"/>
    			
    		<TextView
    			android:id="@+id/mCharacter"
    			...
    			android:text="@{marvelItem.name}"/>
    		...
    	</layout>

In MarvelCharacterListAdapter, I added the following code to use the above binding
	
    public class MarvelCharacterListAdapter ...
    		...
    		public MarvelCharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    		   LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    		   MarvelCharacterRvItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.marvel_character_rv_item, parent, false);
    		   return new MarvelCharacterViewHolder(binding);
    		}
    		...
    		public void onBindViewHolder(@NonNull MarvelCharacterViewHolder holder, int position) {
    			...
    			holder.bind(marvelCharacter);
    		}
    	
    		class MarvelCharacterViewHolder extends RecyclerView.ViewHolder {
    			private final ViewDataBinding binding;
    
    			public MarvelCharacterViewHolder(ViewDataBinding binding) {
    			   super(binding.getRoot());
    			   this.binding = binding;
    			}
    
    			public void bind(ProcessedMarvelCharacter obj) {
    				binding.setVariable(BR.marvelItem, obj);
    				binding.executePendingBindings();
    			}
    		}
    	}

I would like to point out that the image view loading was taken care by @BindingAdapter("url"). I had created this earlier for the detail page display. Yay to reusable code! 

## How can I optimizing the refresh list calls in the Recyclerview?

The most common way to refresh a recycler view is via notifyDataSetChanged or notifyItemChanged. However, sometimes the list will change in parts and at different places. 

DiffUtils calculates the minimal number of updates to convert one list into another. However, the calculateDiff function runs on the main thread and blocks the thread if the difference between two lists is too large. The algorithm AsyncListDiffer does the same job but on a background thread.

I integrated the class AsyncListDiffer in MarvelCharacterListAdapter and provided the areItemsTheSame and areContentsTheSame in the ItemCallback for the algo.
		
		public class MarvelCharacterListAdapter ...
			...
			private final AsyncListDiffer<ProcessedMarvelCharacter> differ = new AsyncListDiffer<ProcessedMarvelCharacter>(this, diffCallBack);
			public static final DiffUtil.ItemCallback<ProcessedMarvelCharacter> diffCallBack = new DiffUtil.ItemCallback<ProcessedMarvelCharacter>() {
				@Override
				public boolean areItemsTheSame(@NonNull ProcessedMarvelCharacter oldItem, @NonNull ProcessedMarvelCharacter newItem) {
					return (oldItem.name.compareToIgnoreCase(newItem.name) == 0);
				}

				@Override
				public boolean areContentsTheSame(@NonNull ProcessedMarvelCharacter oldItem, @NonNull ProcessedMarvelCharacter newItem) {
					return (oldItem.imageurl.compareToIgnoreCase(newItem.imageurl) == 0);
				}
			};
			...
			public void onBindViewHolder(@NonNull MarvelCharacterViewHolder holder, int position) {
				ProcessedMarvelCharacter marvelCharacter = differ.getCurrentList().get(position);
				...
			}
			...
			public int getItemCount() {
				return differ.getCurrentList().size();
			}
			public void submitList(List<ProcessedMarvelCharacter> list) {
				differ.submitList(list);
			}
			...
		}
	

## More Optimizations! How to stop the list from reloading on configuration changed?

I noticed that on configuration change the list of marvel characters were all reloaded. This got me to the conclusion that I could now use ViewModel. ViewModel is used to retain the instance of data across configuration changes. 

However, In this case I already have something akin to ViewModel in my project. My controller instance is being injected by Hilt and Hilt allows me to inject my controller using the scope ActivityRetainedComponent. An ActivityRetainedComponent lives across configuration changes, so it is created at the first Activity#onCreate() and destroyed at the last Activity#onDestroy().

I created a module with this component and injected CharactersListPageController from here. 

However, this gave compile time error as Hilt was finding two paths of injection (one from this module and one from constructer). Therefore, I had to create an interface AbsCharactersListPageController which was implemented by CharactersListPageController. I then used this interface to create the instance of CharactersListPageController in the module and used it in the activity.

The instance was retained across configuration changes eliminating my need to use ViewModel. I have to find another use case for using ViewModel now :)

I created AbsCharactersListPageController
		
	    public interface AbsCharactersListPageController {
    		LiveData<State> getCharactersLiveData();
    		void loadCharacters();
    		class State {
    			public boolean loading;
    			public boolean error;
    			public List<ProcessedMarvelCharacter> marvelCharactersList;
    
    			public State(boolean loading, boolean error, List<ProcessedMarvelCharacter> marvelCharactersList) {
    				this.loading = loading;
    				this.error = error;
    				this.marvelCharactersList = marvelCharactersList;
    			}
    		}
    	}
   In ActivityModule.java,  I declared
	    
	    @Module
    	@InstallIn(ActivityRetainedComponent.class)
    	public abstract class ActivityRetainedModule {
    		@ActivityRetainedScoped
    		@Binds
    		public abstract AbsCharactersListPageController createCharactersListPageController(
    				CharactersListPageController charactersListPageController
    		);
    	}

## How to bind a generic ClickEventListener to my RecyclerView and trigger actions from it using data binding
I created an Effect class in controller. I created a class instead of using enum with actions defined in it so that effect can be more generic.

    public abstract class Effect {  }

In AbsCharactersListPageController, I declared ClickListener and ClickEffect. Then I passed the ClickListener to the State.
	
	...
	    class State {  
		      ...;  
	        public AbsMarvelCharacterClickedListener marvelCharacterClickedListener;  
	        public State(boolean loading, boolean error, List<ProcessedMarvelCharacter> marvelCharactersList, AbsMarvelCharacterClickedListener marvelCharacterClickedListener) {  
	            ... 
	            this.marvelCharacterClickedListener = marvelCharacterClickedListener;  
	        }  
	    }
        abstract class AbsMarvelCharacterClickedListener<T> {  
            public abstract void invoke(T item);  
        }
        public class ClickEffect<T> extends Effect{  
    	    public T item;  
      
    	    public ClickEffect(T item) {  
    	        this.item = item;  
    	    }  
    	}
  
In activity_main.xml, I pass the listener to recycler view via state

     ...
     <androidx.recyclerview.widget.RecyclerView  
    	  ...
    	  app:marvelCharacterClickHandler="@{state.marvelCharacterClickedListener}"
     ...

Now, I added the listener in the row items.
In marvel_character_rv_item.xml

    ...
    	<data>
    	...
    	<variable  
	      name="clickHandler"  
		  type="com.example.controllers.retrofit.AbsCharactersListPageController.AbsMarvelCharacterClickedListener" />
      </data>
      ...
      <androidx.cardview.widget.CardView
    	  ...
    	  app:item="@{marvelItem}"
    	  app:onClick="@{clickHandler}">
      ....

I declared the tags app:marvelCharacterClickHandler and app:onClick as BindingAdapters in BindingUtils. I used combination of tags and flagged the parameters requireAll as true.

	    @BindingAdapter(value = {"datasource", "marvelCharacterClickHandler"}, requireAll = true)  
	    public static void loadDataSource(RecyclerView rvMarvelCharacters, List<ProcessedMarvelCharacter>marvelCharactersList, AbsCharactersListPageController.AbsMarvelCharacterClickedListener marvelCharacterClickedListener) {   
	      MarvelCharacterListAdapter marvelCharacterListAdapter = new MarvelCharacterListAdapter(marvelCharacterClickedListener);  
	      rvMarvelCharacters.setAdapter(marvelCharacterListAdapter);  
		  marvelCharacterListAdapter.submitList(marvelCharactersList);  
	    }  
	    
	    @BindingAdapter(value = {"onClick", "item"}, requireAll = true)  
	    public static void onCharacterClicked(View view, AbsCharactersListPageController.AbsMarvelCharacterClickedListener clickedListener, Object item) {  
	        view.setOnClickListener(new View.OnClickListener() {  
	            @Override  
			    public void onClick(View view) {  
	                clickedListener.invoke(item);  
	            }  
	        });  
	    }

In MarvelCharacterListAdapter,  I set the ClickListener to the binding of the ViewHolder.

       public class MarvelCharacterListAdapter ...
        	AbsCharactersListPageController.AbsMarvelCharacterClickedListener marvelCharacterClickedListener;
        	public MarvelCharacterListAdapter(AbsCharactersListPageController.AbsMarvelCharacterClickedListener marvelCharacterClickedListener) {  
		        this.marvelCharacterClickedListener = marvelCharacterClickedListener;  
		    }
        	class MarvelCharacterViewHolder ...
        	public void bind(ProcessedMarvelCharacter obj) {
        		...
        		binding.setVariable(BR.clickHandler, marvelCharacterClickedListener);
        		binding.executePendingBindings();
        	}
        }

In CharactersListPageController, I created a LiveData of type Effect. I implemented the invoke function of ClickListener. In the invoke function, I created a ClickEffect instance and posted it to the LiveData of type Effect. 

	    MutableLiveData<Effect> effectLiveData = new MutableLiveData<>();
    	public LiveData<Effect> effectLiveData() { return effectLiveData; }  
    public class MarvelCharacterClickedListener extends AbsMarvelCharacterClickedListener<ProcessedMarvelCharacter> {  
      
        @Override  
      public void invoke(ProcessedMarvelCharacter item) {  
            logger.d("Vartika", "OnMarvelCharacterClicked: "+item);  
            effectLiveData.postValue(new ClickEffect(item));  
        }  
    }
    
In CharactersListActivity, I observe the LiveData of type Effect and when its data changes, I start a new activity of the character details.

    	public class CharactersListActivity ...
    		protected void onCreate(Bundle savedInstanceState) {
    			...
    			controller.effectLiveData().observe(this, effect -> 			
    			{  
    			    setEffect(effect);  
    			}); 
    			...
    		}
    		private void setEffect(Effect effect) {  
    		   if(effect instanceof AbsCharactersListPageController.ClickEffect) {  
    		   ProcessedMarvelCharacter marvelCharacter = (ProcessedMarvelCharacter) ((AbsCharactersListPageController.ClickEffect)effect).item;  
	           Intent intent = new Intent(this, CharacterDetailsActivity.class);  
	           intent.putExtra("MARVEL_CHARACTER_ID", marvelCharacter.id);  
	           startActivity(intent);  
	        }  
	    }
	}
In CharacterDetailsActivity

    public class CharacterDetailsActivity ...
    	protected void onCreate(Bundle savedInstanceState) {  
    	    Intent intent = getIntent();  
    	    characterId = intent.getIntExtra("MARVEL_CHARACTER_ID", 0);
    		...
    	}
    ...
    }

## How could I make my application behave consistently and predictably while moving across activities?

Android jetpack's navigation component eliminates the task of managing navigation and back stack and makes it the framework's job to adhere to principles of navigation. I decided to move to it.

I moved the views from activities to fragment and  created nav_graph with the destination fragments and the actions for the navigation. 

In acitivity_main.xml, I just added a fragment container and marked it as NavHostFragment.

    <androidx.fragment.app.FragmentContainerView  
      ...  
      android:id="@+id/nav_host_fragment"  
      android:name="androidx.navigation.fragment.NavHostFragment"  
      app:defaultNavHost="true"  
      app:navGraph="@navigation/nav_graph"  
      ... />
      
nav_graph.xml is as follows

    <navigation 
	  ... 
      android:id="@+id/nav_graph"  
      app:startDestination="@id/CharactersListFragment">  
      
        <fragment  
		     android:id="@+id/CharactersListFragment"
		     android:name="com.example.rxjavaretrofittest.CharactersListFragment"  
		     android:label="@string/first_fragment_label"
		     tools:layout="@layout/fragment_characters_list">  
		     <action
			     android:id="@+id/action_FirstFragment_to_SecondFragment"
			     app:destination="@id/CharacterDetailFragment" />  
        </fragment>
          
        <fragment
	        android:id="@+id/CharacterDetailFragment"
			android:name="com.example.rxjavaretrofittest.CharacterDetailFragment"  
			android:label="@string/second_fragment_label"
			tools:layout="@layout/fragment_character_details">  
            <action
	            android:id="@+id/action_Second2Fragment_to_First2Fragment"  
	            app:destination="@id/CharactersListFragment" />  
        </fragment>  
    </navigation>

CharactersListFragment contains all the code from the list activity and CharacterDetailFragment contains all the logic from detail activity. 

## How to pass arguments between fragments using androidx navigation?

To pass arguments from list fragment to detail fragment. I used safeargs instead of Bundle as it is the type safe way of parameter passing. 
In the app-level build.gradle, I added dependency for classpath

    classpath "androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0"

In ui module's build.gradle, I applied the plugin

    apply plugin: "androidx.navigation.safeargs"
In nav_graph, I added argument in CharacterDetailFragment to indicate that it would receive ProcessedMarvelCharacter from previous fragment

    <fragment  
      android:id="@+id/CharacterDetailFragment"  
	  ... 
      <argument  
	      android:name="item"  
	      app:argType="com.example.controllers.retrofit.ProcessedMarvelCharacter" />  
    </fragment>

In CharacterListFragment when click event is triggered, CharactersListFragment is launched by passing the item in the generated Directions file.

    CharactersListFragmentDirections.ActionListToDetail directions = CharactersListFragmentDirections.actionListToDetail(marvelCharacter);  
    NavHostFragment.findNavController(CharactersListFragment.this).navigate(directions);
I retrieved it in CharacterDetailFragment via the generated Args file.

    ProcessedMarvelCharacter item = CharacterDetailFragmentArgs.fromBundle(getArguments()).getItem();  
    characterId = item.id;

In order to pass ProcessMarvelCharacter, I had to make it implement Parcelable

    class ProcessedMarvelCharacter implements Parcelable {
	    ...
	    protected ProcessedMarvelCharacter(Parcel in) {  
	        id = in.readInt();  
	        name = in.readString();  
	        imageurl = in.readString();  
	        description = in.readString();  
	        modified = in.readString();  
	    }  
      
	    public static final Creator<ProcessedMarvelCharacter> CREATOR = new Creator<ProcessedMarvelCharacter>() {  
	        @Override  
		    public ProcessedMarvelCharacter createFromParcel(Parcel in) {  
	            return new ProcessedMarvelCharacter(in);  
		     }  
      
	        @Override  
		    public ProcessedMarvelCharacter[] newArray(int size) {  
	            return new ProcessedMarvelCharacter[size];  
	        }  
    	};  
	  
		@Override  
		public int describeContents() {  
		    return 0;  
		}  
	  
		@Override  
		public void writeToParcel(Parcel parcel, int i) {  
		    parcel.writeInt(id);  
		    parcel.writeString(name);  
		    parcel.writeString(imageurl);  
		    parcel.writeString(description);  
		    parcel.writeString(modified);  
		}
	}	

## How to enable up arrow in a single page activity
In the styles.xml, I customized app theme 

    <style name="AppTheme" parent="Theme.MaterialComponents.Light.DarkActionBar.Bridge">  
	    <item name="windowActionBar">false</item>  
        <item name="windowNoTitle">true</item>  
        ...  
    </style>
 In activity_main.xml, I added an AppBarLayout 

    ...
    <com.google.android.material.appbar.AppBarLayout  
      ... 
      android:theme="@style/AppTheme.AppBarOverlay">  
         <androidx.appcompat.widget.Toolbar  
		      android:id="@+id/toolbar"  
		      android:layout_width="match_parent"  
		      android:layout_height="?attr/actionBarSize"  
		      android:background="?attr/colorPrimary"  
		      app:popupTheme="@style/AppTheme.PopupOverlay" />  
        </com.google.android.material.appbar.AppBarLayout>
     ...
In my activity (MarvelActivity), I set up the tool bar

    private void setUpToolBar() {  
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);  
        NavController navController = navHostFragment.getNavController();   
        AppBarConfiguration appBarConfiguration =  new AppBarConfiguration.Builder(navController.getGraph()).build();  
        Toolbar toolbar = findViewById(R.id.toolbar);  
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);  
    }

How can I apply some cool transitions between list and detail screen

<img alt="Fragment Transition" src="https://github.com/svartika/MarvelApp/blob/master/documents/fragmentTransitions.gif" width="300" />

