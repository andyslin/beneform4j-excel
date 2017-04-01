@echo off
setlocal
rem ---设置参数---
set sid=%1
set path=%2
set batch=%3

rem ---设置路径---
set CtlFile=%path%/%batch%.ctl
set LogFile=%path%/%batch%.log
set BadFile=%path%/%batch%.bad

rem ---清空中间文件---
rem del /Q %path%/*.bad

rem ---导数---
D:\oracle\client\product\12.1.0\client_1\BIN\sqlldr.exe userid=%sid% parallel=false errors = 0 control=%CtlFile% log=%LogFile% bad=%BadFile% silent=feedback

endlocal

exit