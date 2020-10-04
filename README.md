# MarvelApp ![Android CI](https://github.com/svartika/MarvelApp/workflows/Android%20CI/badge.svg)

I am trying to make a simple application to view Marvel Characters and their details.

The technologies that I am using here are -> RxJava2, Retrofit, Hilt, MVC Architecture, LiveData, Data binding & Glide
This is a project in progress and I will be updating the newer things as and when I use them.

The first screen(CharactersListActivity) fetches the list of all characters and displays it in a recycler view.

The second screen(CharacterDetailsActivity) shows the details of a character.

I integrated the following libraries
RxJava2 ->
	implementation 'io.reactivex.rxjava2:rxjava:2.2.8'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
	Note: I did not use RxJava3 as its adapter for retrofit was still meant for RxJava2.
	implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.2.1'
Retrofit ->
	implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.4.0'

At first, My first screen simply used Retrofit to get the list of marvel characters.
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
	
	marvelRetrofitEndpointApi is defined in the retrofit endpoint interface  as follows
	public interface MarvelRetrofitEndpointApi {
		@GET("/v1/public/characters")
		Observable<MarvelCharactersLoadResponse> loadCharacters();
	}
	
	
I worked on separating the files in to different modules in order to follow the principles of Clean Architecture and Model View Controller
	I made the following modules 
	App -> this was aware of all the components in my project.
	UI -> this contains the View components.
	Controller -> this contains the business logic. I only included contract for the client here in form of interfaces. (dependency inversion principle)
	ControllerImplementation -> this contains the implementation files for the contract that the controller made with the client. As of now it is only the network call related implementation (Retrofit)
	Entity -> this contains the Model components. This module is expected to change only when we make fundamental changes to the application. 
	This organization helped me to separate the project in to clear roles and responsibility. And since all the modules were not changed everytime I made changes, it reduced the build time.
	
	<<<include image here>>>
	
	[alt text](https://github.com/svartika/MarvelApp/blob/master/documents/MVC.jpg?raw=true)
	
At this point I started to integrate Dagger2 for dependency injection in my project. However, Android developer asked me to use Hilt. The documentation is well written. I do not know Dagger2 but I was able to use Hilt with out many issues. 
I created my Logger class and used contructor and property based dependency injection for it.
I modified retrofit to get initialized using method based dependency injection

	a) For hilt I included the following libraries 
	apply plugin: 'kotlin-android'
	apply plugin: 'kotlin-kapt'
	apply plugin: 'dagger.hilt.android.plugin'
	implementation "com.google.dagger:hilt-android:$hilt_version"
    kapt "com.google.dagger:hilt-android-compiler:$hilt_version"
	
	b) In my Application class I gave the annotation @HiltAndroidApp
	@HiltAndroidApp
	public class MarvelApplication extends Application {}
	
	c) I added the Logger class in entity and marked the constructer with annotation @Inject
	public class Logger {
		@Inject
		public Logger() {

		}

		public void d(String tag, String message) {
			Log.d(tag, message);
		}
	}
	
	d) constuctor based dependency injection
	@Inject
    Logger logger;
	
	e) In order to try out the property based dependency injection, I used it as follows in the NetworkImpementationController class
	Logger logger; //declaration
	@Inject
    public CharactersListNetworkInterfaceImpl(Logger logger) { //passed as a parameter in the constructer
        this.logger = logger; 
    }
	
	f) Now, I moved to porting the retrofit instantiation to Hilt
	I created Hilt Module with Binds for creating my network controller 
	@Module
	@InstallIn(ApplicationComponent.class) // inject the binding in Application class -> this is about scoping..keeping the instance alive 
	public abstract class InjectionModule {
		@Binds
		public abstract CharactersListNetworkInterface createRetrofit(
			CharactersListNetworkInterfaceImpl retrofitImpl
		);
	}
	
	I use this in the CharactersListActivity as follows
	@Inject
    CharactersListNetworkInterface charactersListNetworkInterface; // declaration
	Observable<List<MarvelCharacter>> marvelCharacters = charactersListNetworkInterface.loadMarvelCharacters(); //usage
	
	The loadMarvelCharacters() is implemented in class CharactersListNetworkInterfaceImpl 
	public class CharactersListNetworkInterfaceImpl implements CharactersListNetworkInterface {
		@Inject
		MarvelRetrofitEndpointApi marvelRetrofitEndpointApi;
		public Observable<List<MarvelCharacter>> loadMarvelCharacters() {
			Observable<List<MarvelCharacter>> call = marvelRetrofitEndpointApi.loadCharacters().map(
					marvelCharactersLoadResponse -> {
						return marvelCharactersLoadResponse.data.characters;
					}
			);
			return call;
		}
	}
	
	I then created MarvelRetrofitEndpointApi marvelRetrofitEndpointApi. 
	I had to use the @Provides annotation because the instance of EndpointApi had to be created using builder pattern. 
	The function body in the annotated function tells Hilt how to provide an instance of the corresponding type. Hilt executes the function body every time it needs to provide an instance of that type.
	@Module
	@InstallIn(ApplicationComponent.class)
	public class InjectionModule2 {
		@Provides
		public MarvelRetrofitEndpointApi getEndPoint(@Marvel Retrofit retrofit) {
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
			OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new QueryInterceptor()).addInterceptor(loggingInterceptor).build();
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
	//This means that if Retrofit bindRetrofit() has multiple bindings, I can use the annotation @Marvel to pick the binding I need

My first screen is quite ok now.
I used the above mentioned libraries in my second screen as well.

I now want to fetch marvel character detail in a State element and then use Livedata to notify UI component.
The UI component in turn will use data binding to update itself.

In class CharacterDetailPageController, 
	I created a class State to hold information about the Character, error and/or progress.
	I created MutableLiveData of type State. I provided a getter for this LiveData, so the activity can access it.
	I then changed my retrofit to post this LiveData once RxJava2 Observable completes its subscribe.
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
	I created a DataBinding for this activity
	I observed on the LiveData which I had created earlier in the CharacterDetailPageController. 
	Once I received the State variable, I simply passed this value to the binding which set this variable in the layout xml

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
	I have added a data element with the variable element. I have received the State variable here and passed it on to the content layout.
	...
	<data>
        <variable
            name="state"
            type="com.example.controllers.retrofit.CharacterDetailPageController.State" />
    </data>
	...
	<include layout="@layout/content_character_detail"
        app:contentstate="@{state}"/>
		
	In content_character_detail.xml, I have used this variable to set the name and load image for the marvel character using Glide
	//setting name was just a matter of using the name property in the ProcessedMarvelCharacter.
	//setting image required me to use Glide 
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
	
	So, You can see above that I have created an element called url in the ImageView.
	I need to create a static function with annotation @BindingAdapter(url) to receive the url value and load it in ImageView using Glide.
	I included the following libraries for Glide
	implementation 'com.github.bumptech.glide:glide:4.11.0'
    kapt 'com.github.bumptech.glide:compiler:4.11.0'
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
	
	I am going to try to implement data binding in the first screen also now.
	I created CharactersListPageController to hold the instance of CharactersListNetworkInterface. This will load the marvel characters using retrofit and pass these to the processed marvel characters list in the State variable. The State is held in a MutableLiveData which is observed by the activity.
	public class CharactersListPageController {
		MutableLiveData<State> charactersLiveData = new MutableLiveData<State>();
		@Inject
		CharactersListNetworkInterface charactersListNetworkInterface;
		@Inject
		Logger logger;
		@Inject
		CharactersListPageController() { }

		public void loadCharacters() {
			charactersLiveData.postValue(new State(true, false, null));
			Log.d("VartikaHilt", "retrofitController object hash -> " + charactersListNetworkInterface.hashCode());
			Observable<List<ProcessedMarvelCharacter>> marvelCharacters = charactersListNetworkInterface.loadMarvelCharacters();
			Disposable disposable = marvelCharacters
					.subscribeOn(Schedulers.io()) //create a background worker thread on which observable will carry out its task
					.observeOn(AndroidSchedulers.mainThread()) //get hold of the main thread so that results can be sent back to the UI
					.subscribe(marvelCharactersList -> {
								Log.d("VartikaHilt", "Marvel Characters: " + marvelCharactersList.size());
								charactersLiveData.postValue(new State(false, false, marvelCharactersList));
								//displayMarvelCharacters(marvelCharactersList);
							},
							err -> {
								Log.d("VartikaHilt", err.getLocalizedMessage());
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
	<layout 
		xmlns:android="http://schemas.android.com/apk/res/android"
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
	
	Next, we need to pass this to the individual elements in the recycler view. 
	In the item layout, marvel_character_rv_item.xml, i just added the data element and used it to populate the items.
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
	I would like to point out that the image view loading was taken care by @BindingAdapter("url"). I had created this earlier for the detail page display.
	
	Now I am going to try to use an algorithm AsyncListDiffer with recycler view. This will take care of changing the list elements with updates happening in the updated elements. The algo works in the background while the diff is being calculated.
	In MarvelCharacterListAdapter I integrated the class AsyncListDiffer and provided the areItemsTheSame and areContentsTheSame in the ItemCallback for the algo.
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
	
	At this point, I noticed that on configuration change the list of marvel characters were all reloaded. This got me to the point where I could now learn about ViewModel. It is used to retain the instance of data across configuration changes. However, In this case I have something akin to ViewModel in my project. My controller instance is being injected by Hilt. Hilt allows me to inject my controller ActivityRetainedComponent. ActivityRetainedComponent lives across configuration changes, so it is created at the first Activity#onCreate() and destroyed at the last Activity#onDestroy().
	I created a module with this component and injected CharactersListPageController from here. However, this gave compile time error as Hilt was finding two paths of injection (one from activity module and one from constructer). Therefore, I had to create an interface AbsCharactersListPageController which was implemented by CharactersListPageController. I then created this in the module and used it in the activity. The instance was retained across configuration changes eliminating my need to use ViewModel. I have to find another use case for learning ViewModel now :)

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
	In ActivityModule.java, i declared
	@Module
	@InstallIn(ActivityRetainedComponent.class)
	public abstract class ActivityRetainedModule {
		@ActivityRetainedScoped
		@Binds
		public abstract AbsCharactersListPageController createCharactersListPageController(
				CharactersListPageController charactersListPageController
		);
	}
