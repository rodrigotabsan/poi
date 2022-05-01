/*
 *  ====================================================================
 *    Licensed to the Apache Software Foundation (ASF) under one or more
 *    contributor license agreements.  See the NOTICE file distributed with
 *    this work for additional information regarding copyright ownership.
 *    The ASF licenses this file to You under the Apache License, Version 2.0
 *    (the "License"); you may not use this file except in compliance with
 *    the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * ====================================================================
 */

package org.apache.poi.xssf.usermodel;

import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCfvo;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STCfvoType;

/**
 * High level representation for Icon / Multi-State / Databar /
 *  Colour Scale change thresholds
 */
public class XSSFConditionalFormattingThreshold implements org.apache.poi.ss.usermodel.ConditionalFormattingThreshold {
    private final CTCfvo cfvo;

    protected XSSFConditionalFormattingThreshold(CTCfvo cfvo) {
        this.cfvo = cfvo;
    }

    protected CTCfvo getCTCfvo() {
        return cfvo;
    }

    @Override
    public RangeType getRangeType() {
        return RangeType.byName(cfvo.getType().toString());
    }
    @Override
    public void setRangeType(RangeType type) {
        STCfvoType.Enum xtype = STCfvoType.Enum.forString(type.name);
        cfvo.setType(xtype);
    }

    @Override
    public String getFormula() {
        boolean isFormula = null;
        if (cfvo.getType() == STCfvoType.FORMULA) {
            isFormula = cfvo.getVal();
        }
        return isFormula;
    }
    @Override
    public void setFormula(String formula) {
        cfvo.setVal(formula);
    }

    @Override
    public Double getValue() {
        Double value = null;
        boolean isTypeEqualsToTypeFormulaMinOrMax = cfvo.getType() == STCfvoType.FORMULA ||
            cfvo.getType() == STCfvoType.MIN ||
            cfvo.getType() == STCfvoType.MAX;
        if (!isTypeEqualsToTypeFormulaMinOrMax && cfvo.isSetVal()) {
            value Double.parseDouble(cfvo.getVal());
        }
        return value;
    }
    
    @Override
    public void setValue(Double value) {
        if (value == null) {
            cfvo.unsetVal();
        } else {
            cfvo.setVal(value.toString());
        }
    }
}
