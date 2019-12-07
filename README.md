# QuickAnimation
## 前言
QuickAnimation整合了现有的view动画和属性动画，简化了在日常动画使用过程中繁琐的调用过程。同时引入了编译时注解，自动生成动画类，进一步减少了开发者编写动画的工作量。
## 使用方式
当前暂时还未提供jcenter库地址
### 注解调用方式
当前提供了常用的属性动画以及view动画注解，包括：@Alpha、@Rotate、@Scale、@Translate、@ResourceAnimation、@Animator、
、Animators以及@AnimationParams、@TypeEvaluator、@Interpolator 4个参数注解.

例:
旋转动画  
@Interpolator(DecelerateInterpolator.class)  
@Rotate(fromDegree = 0, toDegree = 360f, pivotXType =1, pivotYType = 1, pivotXValue = 0.5f, pivotYValue = 0.5f)   @AnimationParams(duration = 1000, delayTime = 100)  
TextView rotate;

平移动画   
@AnimationParams(duration = 1000, delayTime = 100)  
@Translate(fromX = 0, toX = 100, fromY = 0, toY = 100)  
TextView translate;

透明度动画  
@AnimationParams(duration = 1000, delayTime = 100)  
@Alpha(fromAlpha = 1f, toAlpha = 0.5f)  
TextView alpha;

缩放动画  
@AnimationParams(duration = 1000, delayTime = 100)  
@Scale(fromX = 1, toX = 2, fromY = 1, toY = 2, pivotXType = 1, pivotXValue = 0.5f, pivotYType = 1,pivotYValue = 0.5f)  TextView scale;

xml方式  
@ResourceAnimation(animationId = R.anim.my_animation)  
TextView resource;

属性动画  
@AnimationParams(duration = 1000, delayTime = 100)  
@Animator(property = "TranslationX", value = {0, 100})  
TextView animator;

多个属性动画  
@AnimationParams(duration = 1000, delayTime = 100)  
@TypeEvaluator(TestEvaluator.class)  
@Interpolator(DecelerateInterpolator.class)  
@Animators(value = {@Animator(property = "TranslationX", value = {0, 100}, autoCancel = true),
            @Animator(property = "TranslationY", value = {0, 100}, autoCancel = true)}, playTogether = true)  
TextView animators;

多个view动画  
@Interpolator(DecelerateInterpolator.class)  
@AnimationParams(duration = 1000, delayTime = 100)  
@Translate(fromX = 0, toX = 100, fromY = 0, toY = 100)  
@Alpha(fromAlpha = 1f, toAlpha = 0.5f)  
TextView animations;

调用一次build  project, android plugin会自动为当前activity/fragment生成一个当前类名+.Generator的注解类并为当前注解的view生成静态方  
例：  
public void translate(View view) {  
      AnnotationTestActivity1_Generator.playTranslateAnimations(this);  
      }

## 代码调用方式.  
QuickAnimation.with(Activity activity)  
例：  
QuickAnimation.with(this)  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;QuickAnimation.with(this)  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;.repeatMode(QuickAnimation.REVERSE)  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;.repeatCount(1).  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;duration(1000)  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;.delay(100)  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;.interpolator(new AccelerateInterpolator())  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;.asScale(1, 2, 1, 2, QuickAnimation.ABOSOLUTE, view.getWidth() / 2f, QuickAnimation.ABOSOLUTE, view.getHeight() / 2f)  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;.play(view);               
