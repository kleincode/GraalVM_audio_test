# Java Sound API in GraalVM
All files are in `src/main/java`. Tested in several configurations on a MacBook M3 Pro:
```
 Java version: 21.0.2+13, vendor version: GraalVM CE 21.0.2+13.1
 Graal compiler: optimization level: 2, target machine: armv8-a
 C compiler: cc (apple, arm64, 15.0.0)
 Garbage collector: Serial GC (max heap size: 80% of RAM)
```

```
 Java version: 22.0.2+9, vendor version: GraalVM CE 22.0.2+9.1
 Graal compiler: optimization level: 2, target machine: armv8-a
 C compiler: cc (apple, arm64, 15.0.0)
 Garbage collector: Serial GC (max heap size: 80% of RAM)
```

```
 Java version: 24+12, vendor version: GraalVM CE 24-dev+12.1
 Graal compiler: optimization level: 2, target machine: armv8-a
 C compiler: cc (apple, arm64, 15.0.0)
 Garbage collector: Serial GC (max heap size: 80% of RAM)
```

# Get mixers (Main)
## Java execution
```shell
$GRAALVM_HOME/bin/javac Main.java
$GRAALVM_HOME/bin/java Main
```
Result:
```
Found mixer Port NDI Audio
Found mixer Port MacBook Pro-Mikrofon
Found mixer Port MacBook Pro-Lautsprecher
Found line SPEAKER target port
Found mixer Port Microsoft Teams Audio
Found line Microsoft Teams Audio target port
Found mixer Port NDI Audio
Found line NDI Audio target port
Found mixer Default Audio Device
Found line interface TargetDataLine supporting 8 audio formats, and buffers of at least 32 bytes
Found mixer NDI Audio
Found line interface TargetDataLine supporting 14 audio formats, and buffers of at least 32 bytes
Found mixer MacBook Pro-Mikrofon
Found line interface TargetDataLine supporting 8 audio formats, and buffers of at least 32 bytes
Found mixer MacBook Pro-Lautsprecher
Found mixer Microsoft Teams Audio
Found line interface TargetDataLine supporting 14 audio formats, and buffers of at least 32 bytes
Found mixer NDI Audio
```

## Basic compilation using native-image
```shell
$GRAALVM_HOME/bin/javac Main.java
$GRAALVM_HOME/bin/native-image Main
./Main
```

Result:
```
No mixer found
```

## Using GraalVM agent
```
$GRAALVM_HOME/bin/javac Main.java
$GRAALVM_HOME/bin/java -agentlib:native-image-agent=config-output-dir=agent_out Main
```
When this is run, all mixers are still visible. But still not in the native image:
```
$GRAALVM_HOME/bin/native-image Main -H:+UnlockExperimentalVMOptions -H:JNIConfigurationFiles=./agent_out/jni-config.json -H:ReflectionConfigurationFiles=./agent_out/reflect-config.json -H:ResourceConfigurationFiles=./agent_out/resource-config.json
./Main
```

Result:
```
No mixer found
```
The same thing happens when using just one of the three config files (all tested).


# ManualMain
Results: Same behaviour as with Main (works with Java, no returned AudioMixers after Graal native-image)
## Java execution
```shell
$GRAALVM_HOME/bin/javac ManualMain.java
$GRAALVM_HOME/bin/java ManualMain
```
Result:
```
Found mixer Default Audio Device
Found line interface TargetDataLine supporting 8 audio formats, and buffers of at least 32 bytes
Found mixer NDI Audio
Found line interface TargetDataLine supporting 14 audio formats, and buffers of at least 32 bytes
Found mixer MacBook Pro-Mikrofon
Found line interface TargetDataLine supporting 8 audio formats, and buffers of at least 32 bytes
Found mixer MacBook Pro-Lautsprecher
Found mixer Microsoft Teams Audio
Found line interface TargetDataLine supporting 14 audio formats, and buffers of at least 32 bytes
Found mixer NDI Audio
Found mixer Port NDI Audio
Found mixer Port MacBook Pro-Mikrofon
Found mixer Port MacBook Pro-Lautsprecher
Found line SPEAKER target port
Found mixer Port Microsoft Teams Audio
Found line Microsoft Teams Audio target port
Found mixer Port NDI Audio
Found line NDI Audio target port
```

## Basic compilation using native-image
```shell
$GRAALVM_HOME/bin/javac ManualMain.java
$GRAALVM_HOME/bin/native-image ManualMain
./ManualMain
```
Result:
```
No mixer found
```

## Using GraalVM agent
```
$GRAALVM_HOME/bin/javac ManualMain.java
$GRAALVM_HOME/bin/java -agentlib:native-image-agent=config-output-dir=agent_out_manual ManualMain
```
When this is run, all mixers are still visible. But still not in the native image:
```
$GRAALVM_HOME/bin/native-image ManualMain -H:+UnlockExperimentalVMOptions -H:JNIConfigurationFiles=./agent_out_manual/jni-config.json -H:ReflectionConfigurationFiles=./agent_out_manual/reflect-config.json -H:ResourceConfigurationFiles=./agent_out_manual/resource-config.json
./ManualMain
```

Result:
```
No mixer found
```
