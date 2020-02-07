**VirtualApk���벽�裺**

# һ������Ӧ������VirtualApk #

###1������Ŀ��build.gradle�ļ��м�������:

    dependencies {
        classpath 'com.didi.virtualapk:gradle:0.9.8.6'
    }

������gradle�ļ����£�

    // Top-level build file where you can add configuration options common to all sub-projects/modules.

	buildscript {
    
	    repositories {
	        google()
	        jcenter()
	    }
	    dependencies {
	        classpath 'com.android.tools.build:gradle:3.1.4'
	        classpath 'com.didi.virtualapk:gradle:0.9.8.6'
	        
	
	        // NOTE: Do not place your application dependencies here; they belong
	        // in the individual module build.gradle files
	    }
	}

	allprojects {
	    repositories {
	        google()
	        jcenter()
	    }
	}

	task clean(type: Delete) {
	    delete rootProject.buildDir
	}


###2����app��build.gradle�ļ��м�������:

    apply plugin: 'com.didi.virtualapk.host'

	dependencies {
    	implementation 'com.didi.virtualapk:core:0.9.8'
	}

������gradle�ļ�����:

	    apply plugin: 'com.android.application'
		apply plugin: 'com.didi.virtualapk.host'
		
		android {
		    compileSdkVersion 28
		    defaultConfig {
		        applicationId "com.wangyz.virtualapk.host"
		        minSdkVersion 21
		        targetSdkVersion 28
		        versionCode 1
		        versionName "1.0"
		        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
		    }
		    buildTypes {
		        release {
		            minifyEnabled false
		            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
		        }
		    }
		}
	
		dependencies {
		    implementation fileTree(dir: 'libs', include: ['*.jar'])
		    implementation 'com.android.support:appcompat-v7:28.+'
		    implementation 'com.android.support.constraint:constraint-layout:1.0.2'
		    testImplementation 'junit:junit:4.12'
		    androidTestImplementation 'com.android.support.test:runner:1.0.1'
		    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.1'
		
		    implementation 'com.didi.virtualapk:core:0.9.8'
		}


###3���½���Ŀ��Application���̳���Application,����attachBaseContext�����г�ʼ��

    public class App extends Application{

	    @Override
	    protected void attachBaseContext(Context base) {
	        super.attachBaseContext(base);
	        PluginManager.getInstance(base).init();
	    }
	}

###4����AndroidManifest.xml�������Զ����Application

    <application
        android:name=".App"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

###5������Ȩ��

    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

# ����Plugin������VirtualApk #

###1������Ŀ��build.gradle�ļ��м�������:

    dependencies {
        classpath 'com.didi.virtualapk:gradle:0.9.8.6'
    }

������gradle�ļ�����:

    // Top-level build file where you can add configuration options common to all sub-projects/modules.

	buildscript {
	    
	    repositories {
	        google()
	        jcenter()
	    }
	    dependencies {
	        classpath 'com.android.tools.build:gradle:3.1.4'
	        classpath 'com.didi.virtualapk:gradle:0.9.8.6'
	        
	
	        // NOTE: Do not place your application dependencies here; they belong
	        // in the individual module build.gradle files
	    }
	}
	
	allprojects {
	    repositories {
	        google()
	        jcenter()
	    }
	}
	
	task clean(type: Delete) {
	    delete rootProject.buildDir
	}


###2����app��build.gradle�ļ��м�������:

    apply plugin: 'com.didi.virtualapk.plugin'

	virtualApk{
	    packageId = 0x6f
	    targetHost = '../../VirtualAPKHost/app'//����Ӧ�õ�appģ��·��
	    applyHostMapping = true
	}


###3����app��build.gradle�ļ��м���ǩ������

	signingConfigs{
	        release{
	            storeFile file('../../android.keystore')
	            storePassword "android"
	            keyAlias "android"
	            keyPassword "android"
	        }
	    }

		buildTypes {
        release {
            minifyEnabled false
            signingConfig signingConfigs.release
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }


������gradle�ļ�����:

    apply plugin: 'com.android.application'
	apply plugin: 'com.didi.virtualapk.plugin'
	
	android {
	    compileSdkVersion 28
	    defaultConfig {
	        applicationId "com.wangyz.virtualapk.plugin"
	        minSdkVersion 21
	        targetSdkVersion 28
	        versionCode 1
	        versionName "1.0"
	        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
	    }
	    signingConfigs{
	        release{
	            storeFile file('../../android.keystore')
	            storePassword "android"
	            keyAlias "android"
	            keyPassword "android"
	        }
	    }
	    buildTypes {
	        release {
	            minifyEnabled false
	            signingConfig signingConfigs.release
	            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
	        }
	    }
	}
	
	dependencies {
	    implementation fileTree(dir: 'libs', include: ['*.jar'])
	    implementation 'com.android.support:appcompat-v7:28.+'
	    implementation 'com.android.support.constraint:constraint-layout:1.0.2'
	    testImplementation 'junit:junit:4.12'
	    androidTestImplementation 'com.android.support.test:runner:1.0.1'
	    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.1'
	}
	
	virtualApk{
	    packageId = 0x6f
	    targetHost = '../../VirtualAPKHost/app'
	    applyHostMapping = true
	}

		
**ע�⣺PluginӦ�õ���Դ�ļ����ܺ���������Դ�ļ����������������ɲ��APKʱ�ᱨ��:**

![error.png](https://upload-images.jianshu.io/upload_images/3381990-7136f0d5a79e3407.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


**�����ģ����Դ������ģ������ͷ��**

###4�����ɲ��APK

��gradle���ڣ�˫��assemblePlugin������APK

![build.png](https://upload-images.jianshu.io/upload_images/3381990-4ac9dbfc23bbd025.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


�ļ�����Ŀ¼��app/build/outputs/plugin/release/

# ����������Ӧ���м��ز��APK #

###1�������ɵĲ��APK����(ͨ���������adb��)���ֻ�ָ��·������/sdcard/Plugin.apk��

###2��������Ӧ���м���APK

    private static final String PLUGIN_PACKAGE_NAME = "com.wangyz.virtualapk.plugin";
    private static final String PLUGIN_NAME = "com.wangyz.virtualapk.plugin.MainActivity";
	
	private void loadPlugin() {
        try {
            String pluginPath = Environment.getExternalStorageDirectory().getAbsolutePath().concat("/Plugin.apk");
            File plugin = new File(pluginPath);
            PluginManager.getInstance(this).loadPlugin(plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


###3������APK�е�Activity

    public void loadPlugin(View view) {
        if (PluginManager.getInstance(this).getLoadedPlugin(PLUGIN_PACKAGE_NAME) == null) {
            Toast.makeText(getApplicationContext(), "δ���ز��", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(PLUGIN_PACKAGE_NAME, PLUGIN_NAME));
        startActivity(intent);
    }
