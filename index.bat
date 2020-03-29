echo off
echo NUL>_.class&&echo NUL>_.log&&del /s /f /q *.class&&del /s /f /q *.log
cls
javac -cp com/krzem/light_simulation/modules/purejavahidapi.jar;com/krzem/light_simulation/modules/jna-4.0.0.jar; com/krzem/light_simulation/*.java&&java -cp com/krzem/light_simulation/modules/purejavahidapi.jar;com/krzem/light_simulation/modules/jna-4.0.0.jar; com/krzem/light_simulation/Main
start /min cmd /c "echo NUL>_.class&&del /s /f /q *.class"