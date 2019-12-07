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
@Rotate(fromDegree = 0, toDegree = 360f, pivotXType = 1, pivotYType = 1, pivotXValue = 0.5f, pivotYValue = 0.5f)
@AnimationParams(duration = 1000, delayTime = 100)
TextView rotate;

平移动画			
@AnimationParams(duration = 1000, delayTime = 100)
@Translate(fromX = 0, toX = 100, fromY = 0, toY = 100)
TextView translate;

透明度动画       
@AnimationParams(duration = 1000, delayTime = 100)
@Alpha(fromAlpha = 1f, toAlpha = 0.5f)
TextView alpha;

