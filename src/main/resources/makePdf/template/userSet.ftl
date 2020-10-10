<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <title></title>
</head>

<body style="font-size:12px; font-family:SimSun; width: 210mm;">
    <p style="margin:0pt 0pt 10pt;text-align:center;font-family:SimSun; font-size:15pt; font-weight:bold">${name}</p>
<#if '1'==part1!>
    <p style="font-family:SimSun; font-size:15pt; font-weight:bold; margin:10px 0 10px 20px">课堂秩序</p>
    <p style="font-family:SimSun; font-size:15pt;margin: 25px 0;">一、总体情况</p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">${part1_1}</p>
    <p style="font-family:SimSun; font-size:15pt;margin: 25px 0;">二、详细情况</p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">教师违纪扣分前5名:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center"><img src="${part1_2_1}" alt="" width="500" height="320" /></p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">学生违纪扣分前5名:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center"><img src="${part1_2_2}" alt="" width="500" height="320" /></p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">教师各违纪类型发生次数详情:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center"><img src="${part1_2_3}" alt="" width="500" height="320" /></p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">学生各违纪类型发生次数详情:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center"><img src="${part1_2_4}" alt="" width="500" height="320" /></p>
    <p style="font-family:SimSun; font-size:15pt;margin: 25px 0;">三、总结</p>
    <p style="font-family:SimSun; font-size:10pt;text-indent: 35px;">${part1_3}</p>
</#if>
<#if '1'==part2!>
    <p style="font-family:SimSun; font-size:15pt; font-weight:bold; margin:10px 0 10px 20px">课堂质量</p>
    <p style="font-family:SimSun; font-size:15pt;margin: 25px 0;">一、总体情况</p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">
        ${part2_1}
    </p>
    <p style="font-family:SimSun; font-size:15pt;margin: 25px 0;">二、详细情况</p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">教师教研得分排名前5名:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center">
        <img src="${part2_2_1}" alt="" width="500" height="320" />
    </p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">教师教研得分排名后5名:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center">
        <img src="${part2_2_2}" alt="" width="500" height="320" />
    </p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">各评价项得分详情:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center">
        <img src="${part2_2_3}" alt="" width="500" height="320" />
    </p>
    <p style="font-family:SimSun; font-size:15pt;margin: 25px 0;">三、总结</p>
    <p style="font-family:SimSun; font-size:10pt;text-indent: 35px;">
        ${part2_3}
    </p>
</#if>
<#if '1'==part3!>
    <p style="font-family:SimSun; font-size:15pt; font-weight:bold; margin:10px 0 10px 20px">学生出勤</p>
    <p style="font-family:SimSun; font-size:15pt;margin: 25px 0;">一、总体情况</p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">
        ${part3_1}
    </p>
    <p style="font-family:SimSun; font-size:15pt;margin: 25px 0;">二、详细情况</p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">出勤率排名前5:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center">
        <img src="${part3_2_1}" alt="" width="500" height="320" />
    </p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">出勤率排名后5:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center">
        <img src="${part3_2_2}" alt="" width="500" height="320" />
    </p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">课程热度排名前5:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center">
        <img src="${part3_2_3}" alt="" width="500" height="320" />
    </p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">课程热度排名后5:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center">
        <img src="${part3_2_4}" alt="" width="500" height="320" />
    </p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">教师热度排名前5:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center">
        <img src="${part3_2_5}" alt="" width="500" height="320" />
    </p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">教师热度排名后5:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center">
        <img src="${part3_2_6}" alt="" width="500" height="320" />
    </p>
    <p style="font-family:SimSun; font-size:15pt;margin: 25px 0;">三、总结</p>
    <p style="font-family:SimSun; font-size:10pt;text-indent: 35px;">
        ${part3_3}
    </p>
</#if>
<#if '1'==part4!>
    <p style="font-family:SimSun; font-size:15pt; font-weight:bold; margin:10px 0 10px 20px">教师考勤</p>
    <p style="font-family:SimSun; font-size:15pt;margin: 25px 0;">一、总体情况</p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">
        ${part4_1}
    </p>
    <p style="font-family:SimSun; font-size:15pt;margin: 25px 0;">二、详细情况</p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">教师考勤异常次数比列排行前5:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center">
        <img src="${part4_2_1}" alt="" width="500" height="320" />
    </p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">教师考勤异常次数比列排行后5:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center">
        <img src="${part4_2_2}" alt="" width="500" height="320" />
    </p>
    <p style="font-family:SimSun; font-size:10pt; text-indent: 35px;">各异常类型发生次数详情:</p>
    <p style="font-family:SimSun; font-size:10pt; text-align:center">
        <img src="${part4_2_3}" alt="" width="500" height="320" />
    </p>
    <p style="font-family:SimSun; font-size:15pt;margin: 25px 0;">三、总结</p>
    <p style="font-family:SimSun; font-size:10pt;text-indent: 35px;">
        ${part4_3}
    </p>
</#if>
</body>

</html>