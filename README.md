概述：Java poi导入导出EXCEL工具类（兼容各版本）,支持用户模式和SAX事件驱动模式。
![Image text](https://raw.githubusercontent.com/xiaoyunz/ExcelReportHandle/master/img-folder/poi.png)
            
    POI读取Excel有两种模式，一种是用户模式，一种是SAX事件驱动模式，将xlsx格式的文档转换成CSV格式后进行读取。

    用户模式API接口丰富，使用POI的API可以很容易读取Excel，但用户模式消耗的内存很大，当遇到很大sheet、大数据网格，假空行、公式等问题时，很容易导致内存溢出。

    POI官方推荐解决内存溢出的方式使用CVS格式解析，即SAX事件驱动模式。

    下面主要是讲解如何读取大批量数据：

    POI以SAX解析excel2007文件：
    解决思路：通过继承DefaultHandler类，重写process()，startElement()，characters()，endElement()这四个方法。 process()方式主要是遍历所有的sheet，并依次调用startElement()、characters()方法、endElement()这三个方法。
    startElement()用于设定单元格的数字类型（如日期、数字、字符串等等）。 
    characters()用于获取该单元格对应的索引值或是内容值（如果单元格类型是字符串、INLINESTR、数字、日期则获取的是索引值；其他如布尔值、错误、公式则获取的是内容值）。 
    endElement()根据startElement()的单元格数字类型和characters()的索引值或内容值，最终得出单元格的内容值，并打印出来。

    POI通过继承HSSFListener类来解决Excel2003文件：
    解决思路：重写process()，processRecord()两个方法，其中processRecord是核心方法，用于处理sheetName和各种单元格数字类型。

一、功能说明

    允许同时导入或导出多个sheet，同一sheet可同时存在多个数据块，按数据块划分处理数据。

二、配置文件示例及详细说明（以月度销售报表为例）
 
    1、导入xml配置示例如下（见src/main/resources/import-config.xml）
    <?xml version="1.0" encoding="UTF-8"?>
    <imports>
        <!-- 功能说明：允许同时读取多个sheet，允许同一sheet存在多个数据块-->
        <!-- name：报表标识（全局唯一） -->
        <import name="部门月度销售统计">
            <!-- 模板名称，非必须，主要作用：校验import文件名是否匹配 -->
            <template>部门月度销售统计_yyyyMMdd.xlsx</template>
            <!-- 允许sheet配置多个，index：表示sheet序号，不允许重复，excel默认从0开始。name：表示sheet名称 -->
            <sheet index="0" name="月度销售报表明细">
                <!-- 允许同一sheet存在多个数据块（可预见固定数据行数时使用）。 -->
                <!-- index：表示数据块，同一sheet不允许重复。sread：表示从第几行开始读取，excel默认从0计数行 -->
                <!-- sread：对应excel行号，下标从0开始 -->
                <!-- eread：表示读取结束行，不填默认读取到数据行结束。若同一sheet存在多数据块，必须同时配置sread和eread分块读取，若只存在一个数据块，只须配置sread即可 -->
                <row index="1" sread="2">
                    <!-- 配置列，name：列名，field：反射获取实体字段数据，index：列序号，应excel列号，下标从0开始 -->
                    <!-- empty：表示列数据是否允许为空，false（不允许） true（允许） -->
                    <!-- type：表示列数据类型，用于校验读取数据格式正确性，分为6中类型：string（字符型），int（整型），number（浮点型），ldate（长日期14位），sdate（短日期8位），time（短时间6位） -->
                    <column name="序号" field="s_no" index="0" empty="false" type="int"></column>
                    <column name="姓名" field="name" index="1" empty="false" ></column>
                    <column name="部门" field="dep_no" index="2" empty="false" ></column>
                    <column name="销售金额" field="sales_amount" index="3" empty="false"  type="number"></column>
                    <column name="销售日期" field="sales_date" index="4" type="sdate"></column>
                </row>
            </sheet>
            <sheet index="1" name="月度销售报表汇总">
                <row index="1" sread="3" eread="5">
                    <column name="部门" field="dep_no" index="0"></column>
                    <column name="总销售金额" field="sales_amount_total" index="1" type="number"></column>
                    <column name="完成率" field="complete_rate" index="2" type="number"></column>
                    <column name="排名" field="ranking" index="3" type="int"></column>
                </row>
                <row index="2" sread="3" eread="7">
                    <column name="姓名" field="dep_no" index="5"></column>
                    <column name="部门" field="name" index="6"></column>
                    <column name="总销售金额" field="sales_amount_total" index="7" type="number"></column>
                    <column name="完成率" field="complete_rate" index="8" type="number"></column>
                    <column name="排名" field="ranking" index="9" type="int"></column>
                </row>
                <row index="3" sread="3" eread="7">
                    <column name="姓名" field="name" index="11"></column>
                    <column name="基本工资" field="basic_salary" index="12" type="number"></column>
                    <column name="提成工资" field="sales_salary" index="13" type="number"></column>
                    <column name="奖金" field="bonus_salary" index="14" type="number"></column>
                    <column name="总计" field="salary_total" index="15" type="number"></column>
                </row>
            </sheet>
        </import>
    </imports>
    
    2、导出xml配置示例如下（见src/main/resources/export-config.xml）
    <?xml version="1.0" encoding="UTF-8"?>
    <reports>
        <!-- 功能说明：允许同一报表写入多个sheet，允许同一sheet写入多个数据块-->
        <!-- name：报表标识（全局唯一），filename：保存报表的文件名，允许配置多个参数。p{1}：表示第一个占位参数，默认从1开始，在程序中须按顺序加入参数list列表 -->
        <report name="部门月度销售统计" filename="部门月度销售统计_p{1}">
            <!-- 模板名称，非必须，若使用模板才需要配置。template：模板名称 -->
            <template>部门月度销售统计_yyyyMMdd.xlsx</template>
            <!-- 允许sheet配置多个，index：sheet序号，不允许重复。name：sheet名称，允许传入参数 -->
            <sheet index="0" name="月度销售报表明细p{1}" >
                <!-- 允许同一sheet写入多个数据块（建议可预见数据行数使用，否则可能覆盖数据行），以行分隔，表头默认以cloumn中name为写入一行。 -->
                <!-- index：数据块标识，datakey：表示数据块map的key，默认从0开始 -->
                <!-- swrite：表示写入开始行，对应excel行号，下标从0开始 -->
                <row index="1" swrite="2" datakey="0">
                    <!-- 配置sheet column，formatter：列数据格式化，name：列名，field：反射获取实体字段数据，index：列序号，默认从0开始 -->
                    <column formatter="" name="序号" field="s_no" index="0"></column>
                    <column formatter="" name="姓名" field="name" index="1"></column>
                    <column formatter="" name="部门" field="dep_no" index="2"></column>
                    <column formatter="#,##0.00" name="销售金额" field="sales_amount" index="3"></column>
                    <column formatter="" name="销售日期" field="sales_date" index="4"></column>
                </row>
            </sheet>
            <sheet index="1" name="月度销售报表汇总p{1}">
                <row index="1" swrite="3" datakey="1">
                    <column formatter="" name="部门" field="dep_no" index="0"></column>
                    <column formatter="#,##0.00" name="总销售金额" field="sales_amount_total" index="1"></column>
                    <column formatter="#,##0.00" name="完成率" field="complete_rate" index="2"></column>
                    <column formatter="#,##0.00" name="排名" field="ranking" index="3"></column>
                </row>
                <row index="2" swrite="3" datakey="1">
                    <column formatter="" name="姓名" field="name" index="5"></column>
                    <column formatter="" name="部门" field="dep_no" index="6"></column>
                    <column formatter="#,##0.00" name="总销售金额" field="sales_amount_total" index="7"></column>
                    <column formatter="#,##0.00" name="完成率" field="complete_rate" index="8"></column>
                    <column formatter="#,##0.00" name="排名" field="ranking" index="9"></column>
                </row>
                <row index="3" swrite="3" datakey="2">
                    <column formatter="" name="姓名" field="name" index="11"></column>
                    <column formatter="#,##0.00" name="基本工资" field="basic_salary" index="12"></column>
                    <column formatter="#,##0.00" name="提成工资" field="sales_salary" index="13"></column>
                    <column formatter="#,##0.00" name="奖金" field="bonus_salary" index="14"></column>
                    <column formatter="#,##0.00" name="总计" field="salary_total" index="15"></column>
                </row>
            </sheet>
        </report>
    </reports>
三、调用示例

    1、导入：src/test/java/com/reports/test/ExcelImportReportTest.java 

    2、导出：src/test/java/com/reports/test/ExcelExportReportTest.java 
    
四、测试用例及数据

    路径src/test/resources/


