package com.linijumsolutions.gg_sports;

public class MySingleton
{
    private static MySingleton instance;

    public String customVar;

    public static void initInstance()
    {
        if (instance == null)
        {
            instance = new MySingleton();
        }
    }

    public static MySingleton getInstance()
    {
        return instance;
    }

    private MySingleton()
    {
    }

    public void customSingletonMethod()
    {
    }
}