package com.btx.productor.test;

import com.btx.productor.MSProcedor;
import com.btx.productor.imp.MSProcedorImp;

public class Test
{
    public static void main(String[] args)
    {
        MSProcedor msProcedor = new MSProcedorImp();
        msProcedor.init();
        msProcedor.sendMessage("bao-queue");
    }
}
