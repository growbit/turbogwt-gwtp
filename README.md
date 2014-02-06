Turbo GWT (*TurboG*) GWT-Platform Extension
==

**Turbo GWT** is a suite of libs, inspired by famous Javascript MVVM frameworks like Knockout.js and Angular.js, intended to speed up development of GWT applications grounded on the MVP pattern.

**TurboG GWT-Platform** is an extension of TurboG that allows easy integration with GWT-Platform MVP framework.

## Current module integrations
* [TurboG Databind](https://github.com/growbit/turbogwt-databind) - TurboG GWTP provides a [databind module](http://growbit.github.io/turbogwt-gwtp/javadoc/apidocs/index.html) integrating TurboG Databind with GWTP-MVP-Client framework.
 
## Databind (Simple HOW-TO)

See this [test case](https://github.com/growbit/turbogwt-gwtp/tree/master/src/test/java/org/turbogwt/ext/gwtp/databind/client/person) for a more complete example.

## Downloads
TurboG GWTG is currently available at maven central.

### Maven
```
<dependency>
    <groupId>org.turbogwt.ext</groupId>
    <artifactId>turbogwt-gwtp</artifactId>
    <version>0.1.0</version>
</dependency>

<!-- Required dependency -->
<dependency>
    <groupId>org.turbogwt.mvp</groupId>
    <artifactId>turbogwt-databind</artifactId>
    <version>0.1.0</version>
</dependency>
```
