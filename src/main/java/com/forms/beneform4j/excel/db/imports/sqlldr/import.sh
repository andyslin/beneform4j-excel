#!/bin/bash

# ---设置参数
sid=$1
path=$2
batch=$3

# ---设置路径
CtlFile=$path/$batch.ctl
LogFile=$path/$batch.log
BadFile=$path/$batch.bad

#export NLS_LANG=AMERICAN_AMERICA.AL32UTF8
export NLS_LANG="SIMPLIFIED CHINESE"_CHINA.ZHS16GBK

# ---清空中间文件---
rm -rf $path\*.bad

# ---导数---
sqlldr userid=$sid parallel=false errors = 0 control=$CtlFile log=$LogFile bad=$BadFile silent=feedback
retcode=`echo $?`

exit
