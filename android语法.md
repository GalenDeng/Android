## android语法 (2018.07.18)
1. `interface 与 class`
```
interface是不能实例化的，我想你可能是看到这样的代码产生的疑惑
Button.setOnClickListener(new OnClickListener(){...});
因为OnClickListener确实是interface，但是请注意这里是new OnClickListener(){...}而不是new OnClickListener()
其实这里new的不是interface而是interface的匿名实现类
```