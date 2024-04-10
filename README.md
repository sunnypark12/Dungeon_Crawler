
## Table of Contents
Click on the links below to navigate to a specific section!

- [How do I add a screen?](#how-do-i-add-a-screen)
- [I have a screen, now what?](#i-have-a-screen-now-what)

## How do I add a "Screen"?

Adding screens is pretty simple. All you have to do is follow the steps below:

1. In the folder structure (once in Android Studio), go ahead and right click on <mark>layout</mark>.
2. You'll now want to hover over "New", and then to "Fragment" and finally you'll choose "Fragment (Blank)" [or another one, up to you really if you know what you're doing]. 

<img src="documentation/images/as-layout-add-1.png">

3. You'll be met with a dialogue to pick a fragment name, etc.

    - It's not mandatory, but we should probably have a good rule of thumb when it comes to naming things. If you're making a screen, set the "Fragment Name" to Screen***SomethingDescriptive***.
    - You can find exmaples already there. The Fragment Layout Name should be automagically changing.

4. Click Finish. You can now click on the "fragment_screen_somethingdescriptive.xml" file and start editing it either in the code or with the visual editor (double click the xml file if you don't see a visual editor).

## I have a screen, now what?

You have a screen! That's awesome. You'll now need to go to your "ScreenSomethingDescriptive" file in the "app/java/com.example.cs2340s1/" folder.

> This is where the code that actually interacts with your "front-end" will live. Should this screen change from one to another, you can add the following code:

```java
@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // ButtonMainTransition is a button that I made in my ".xml" file and I am importing by getting the ID that I set for it!
        ButtonMainTransition = getActivity().findViewById(R.id.MainScreenTransition);

        // This is the "FragmentHelper" that I made and can be called 
        // from any fragment class by donig a "FragmentHelper fh = FragmentHelper.getInstance();"
        fh.AddTransitionObject(ButtonMainTransition, ScreenPlayerSelect.class, null);

        /**
         * The "fh.AddTransitionObject() method above will take any "view" object (think of this as anything that you can place from the visual editor) and add a listener to it that will transition to the provided "Screen" when it is clicked. This happens automagically by the function so you don't have to worry about it!
         */
    }
```

