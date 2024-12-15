<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!-- Определяем выходной формат как HTML -->
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <!-- Начало преобразования -->
    <xsl:template match="/Medicine">
        <html>
            <head>
                <title>Список лекарств</title>
                <style>
                    table { border-collapse: collapse; width: 70%; margin: 20px auto; }
                    th, td { border: 1px solid black; padding: 8px; text-align: center; }
                    th { background-color: #f2f2f2; }
                </style>
            </head>
            <body>
                <h2 style="text-align: center;">Список лекарств (отсортировано по цене)</h2>
                <table>
                    <tr>
                        <th>Название</th>
                        <th>Фармацевт</th>
                        <th>Группа</th>
                        <th>Вид</th>
                        <th>Цена</th>
                        <th>Количество</th>
                        <th>Тип упаковки</th>
                        <th>Сертификат</th>
                        <th>Дозировка</th>
                    </tr>

                    <xsl:for-each select="Drug">
                        <xsl:for-each select="Versions/Version">
                            <xsl:sort select="Manufacturer/Package/Price" data-type="number"
                                      order="ascending"/>
                            <tr>
                                <td><xsl:value-of select="../../Name"/></td>
                                <td><xsl:value-of select="../../Pharm"/></td>
                                <td><xsl:value-of select="../../Group"/></td>
                                <td><xsl:value-of select="Type"/></td>
                                <td>
                                    <xsl:value-of select="Manufacturer/Package/Price"/> руб.
                                </td>
                                <td>
                                    <xsl:value-of select="Manufacturer/Package/Quantity"/>
                                </td>
                                <td>
                                    <xsl:value-of select="Manufacturer/Package/Type"/>
                                </td>
                                <td>
                                    Номер: <xsl:value-of select="Manufacturer/Certificate/Number"/>,
                                    Дата выдачи: <xsl:value-of
                                        select="Manufacturer/Certificate/IssueDate"/>,
                                    Истечение действия: <xsl:value-of
                                        select="Manufacturer/Certificate/ExpiryDate"/>,
                                    Организация:
                                    <xsl:value-of select="Manufacturer/Certificate/Organization"/>
                                </td>
                                <td>
                                    <xsl:value-of select="Manufacturer/Dosage/Amount"/>,
                                    <xsl:value-of select="Manufacturer/Dosage/Frequency"/>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
