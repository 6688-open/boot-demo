package com.dj.boot.co.handler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ObtainWaybillResponse implements Serializable {
    private GetWaybillDto getWaybillDto;
}
