import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CRCCalcTest {


    @Test
    void getCRCValue() {

        byte[][] TestBuffer01 = {
                {1},
                {0x34, 0x56},
                {(byte)0x89, (byte)0xFF, 0x68, (byte)0x99},
                {'B', 'A', 'N', 'D', 'I'},
                {3, 2, 0, 0, 4, 0, 0, -6, 2, 0, 0, 4, 31, -64, 27, 4, -1, -64, 0,
                 -1, -1, -1, -121, -71, 2, 0, 0, 4, 0, 0, -6, 2, 0, 0, 4, 31, -64,
                27, 4, -1, -60, 0, 26, -79, -7, 127, -10, 2, 0, 0, 4, 0, 0, -6,
                2, 0, 0, 4, 31, -64, 27, 4, -1, -56, 0, -7, -4, 116, 3, -55, 2,
                0, 0, 4, 0, 0, -6, 2, 0, 0, 4, 31, -64, 27},
                {0x03,
                        0x10,0x00,0x58,0x00, (byte) 0xB0,0x00, (byte) 0xC2, (byte) 0x8F,0x04,0x00,0x42, (byte) 0x8C,0x04,0x00,0x42, (byte) 0x8C, (byte) 0xA8,0x00,0x42, 0x24, (byte) 0xE5,
                        0x10,0x00,0x68,0x00,0x00,0x00,0x42, (byte) 0x8C,0x04,0x00,0x42, (byte) 0x8C,0x04,0x00,0x42, (byte) 0x8C,0x00,0x00,0x42, (byte) 0x90,0x44,
                        0x10,0x00,0x78,0x00,0x06,0x00,0x40,0x14,0x00,0x00,0x00,0x00,0x11,0x00, (byte) 0xC2,(byte) 0x93,0x28,0x00, (byte) 0xC4, (byte) 0x8F,0x3D,
                        0x10,0x00, (byte) 0x88,0x00,0x21,0x28,0x40,0x00, (byte) 0xCF,0x7C,0x40,0x0F,0x00,0x00,0x00,0x00,0x34,0x00, (byte) 0xC2, (byte) 0x8F, (byte) 0xC0,
                        0x10,0x00, (byte) 0x98,0x00,0x01,0x00,0x43, (byte) 0x90,0x01,0x00,0x02,0x24,0x64,0x08,0x62,0x14,0x00,0x00,0x00,0x00,0x7B,
                        0x10,0x00, (byte) 0xA8,0x00,0x01,0x00,0x02,0x24,0x23,0x00, (byte) 0xC2, (byte) 0xA3,0x0F, (byte) 0x89,0x40,0x0B,0x00,0x00,0x00,0x00, (byte) 0xB6,
                        0x10,0x00, (byte) 0xB8,0x00,0x2C,0x00, (byte) 0xC2,(byte) 0x8F,0x06,0x00,0x43, (byte) 0x94,0x34,0x00, (byte) 0xC2, (byte) 0x8F,0x04,0x00,0x43, (byte) 0xA4,0x6E,
                        0x10,0x00, (byte) 0xC8,0x00, (byte) 0xB0,0x00, (byte) 0xC2, (byte) 0x8F,0x04,0x00,0x42, (byte) 0x8C,0x04,0x00,0x42, (byte) 0x8C, (byte) 0xB4,0x00,0x42,0x24,0x69,
                        0x10,0x00, (byte) 0xD8,0x00,0x00,0x00,0x42, (byte) 0x8C,0x04,0x00,0x42, (byte) 0x8C,0x04,0x00,0x42, (byte) 0x8C,0x00,0x00,0x43, (byte) 0x90, (byte) 0xD3,
                        0x10,0x00, (byte) 0xE8,0x00,0x03,0x00,0x02,0x24,0x04,0x00,0x62,0x14,0x00,0x00,0x00,0x00,0x34,0x00, (byte) 0xC2, (byte) 0x8F,(byte) 0xE0,
                        0x10,0x00, (byte) 0xF8,0x00,0x03,0x00,0x03,0x24,0x01,0x00,0x43, (byte) 0xA0,0x34,0x00, (byte) 0xC2, (byte) 0x8F,0x02,0x00,0x43, (byte) 0x90, (byte) 0x90
                }};
/*
		[0]	3 '\x3'	char
		[1]	16 '\x10'	char
		[2]	0 '\0'	char
		[3]	88 'X'	char
		[4]	0 '\0'	char
		[5]	-80 '°'	char
		[6]	0 '\0'	char
		[7]	-62 'Â'	char
		[8]	-113 'Ź'	char
		[9]	4 '\x4'	char
		[10]	0 '\0'	char
		[11]	66 'B'	char
		[12]	-116 'Ś'	char
		[13]	4 '\x4'	char
		[14]	0 '\0'	char
		[15]	66 'B'	char
		[16]	-116 'Ś'	char
		[17]	-88 '¨'	char
		[18]	0 '\0'	char
		[19]	66 'B'	char
		[20]	36 '$'	char
		[21]	-27 'ĺ'	char
		[22]	16 '\x10'	char
		[23]	0 '\0'	char
		[24]	104 'h'	char
		[25]	0 '\0'	char
		[26]	0 '\0'	char
		[27]	0 '\0'	char
		[28]	66 'B'	char
		[29]	-116 'Ś'	char
		[30]	4 '\x4'	char
		[31]	0 '\0'	char
		[32]	66 'B'	char
		[33]	-116 'Ś'	char
		[34]	4 '\x4'	char
		[35]	0 '\0'	char
		[36]	66 'B'	char
		[37]	-116 'Ś'	char
		[38]	0 '\0'	char
		[39]	0 '\0'	char
		[40]	66 'B'	char
		[41]	-112 ''	char
		[42]	68 'D'	char
		[43]	16 '\x10'	char
		[44]	0 '\0'	char
		[45]	120 'x'	char
		[46]	0 '\0'	char
		[47]	6 '\x6'	char
		[48]	0 '\0'	char
		[49]	64 '@'	char
		[50]	20 '\x14'	char
		[51]	0 '\0'	char
		[52]	0 '\0'	char
		[53]	0 '\0'	char
		[54]	0 '\0'	char
		[55]	17 '\x11'	char
		[56]	0 '\0'	char
		[57]	-62 'Â'	char
		[58]	-109 '“'	char
		[59]	40 '('	char
		[60]	0 '\0'	char
		[61]	-60 'Ä'	char
		[62]	-113 'Ź'	char
		[63]	61 '='	char
		[64]	16 '\x10'	char
		[65]	0 '\0'	char
		[66]	-120 ''	char
		[67]	0 '\0'	char
		[68]	33 '!'	char
		[69]	40 '('	char
		[70]	64 '@'	char
		[71]	0 '\0'	char
		[72]	-49 'Ď'	char
		[73]	124 '|'	char
		[74]	64 '@'	char
		[75]	15 '\xf'	char
		[76]	0 '\0'	char
		[77]	0 '\0'	char
		[78]	0 '\0'	char
		[79]	0 '\0'	char
		[80]	52 '4'	char
		[81]	0 '\0'	char
		[82]	-62 'Â'	char
		[83]	-113 'Ź'	char
		[84]	-64 'Ŕ'	char
		[85]	16 '\x10'	char
		[86]	0 '\0'	char
		[87]	-104 ''	char
		[88]	0 '\0'	char
		[89]	1 '\x1'	char
		[90]	0 '\0'	char
		[91]	67 'C'	char
		[92]	-112 ''	char
		[93]	1 '\x1'	char
		[94]	0 '\0'	char
		[95]	2 '\x2'	char
		[96]	36 '$'	char
		[97]	100 'd'	char
		[98]	8 '\b'	char
		[99]	98 'b'	char
		[100]	20 '\x14'	char
		[101]	0 '\0'	char
		[102]	0 '\0'	char
		[103]	0 '\0'	char
		[104]	0 '\0'	char
		[105]	123 '{'	char
		[106]	16 '\x10'	char
		[107]	0 '\0'	char
		[108]	-88 '¨'	char
		[109]	0 '\0'	char
		[110]	1 '\x1'	char
		[111]	0 '\0'	char
		[112]	2 '\x2'	char
		[113]	36 '$'	char
		[114]	35 '#'	char
		[115]	0 '\0'	char
		[116]	-62 'Â'	char
		[117]	-93 'Ł'	char
		[118]	15 '\xf'	char
		[119]	-119 '‰'	char
		[120]	64 '@'	char
		[121]	11 '\v'	char
		[122]	0 '\0'	char
		[123]	0 '\0'	char
		[124]	0 '\0'	char
		[125]	0 '\0'	char
		[126]	-74 '¶'	char
		[127]	16 '\x10'	char
		[128]	0 '\0'	char
		[129]	-72 '¸'	char
		[130]	0 '\0'	char
		[131]	44 ','	char
		[132]	0 '\0'	char
		[133]	-62 'Â'	char
		[134]	-113 'Ź'	char
		[135]	6 '\x6'	char
		[136]	0 '\0'	char
		[137]	67 'C'	char
		[138]	-108 '”'	char
		[139]	52 '4'	char
		[140]	0 '\0'	char
		[141]	-62 'Â'	char
		[142]	-113 'Ź'	char
		[143]	4 '\x4'	char
		[144]	0 '\0'	char
		[145]	67 'C'	char
		[146]	-92 '¤'	char
		[147]	110 'n'	char
		[148]	16 '\x10'	char
		[149]	0 '\0'	char
		[150]	-56 'Č'	char
		[151]	0 '\0'	char
		[152]	-80 '°'	char
		[153]	0 '\0'	char
		[154]	-62 'Â'	char
		[155]	-113 'Ź'	char
		[156]	4 '\x4'	char
		[157]	0 '\0'	char
		[158]	66 'B'	char
		[159]	-116 'Ś'	char
		[160]	4 '\x4'	char
		[161]	0 '\0'	char
		[162]	66 'B'	char
		[163]	-116 'Ś'	char
		[164]	-76 '´'	char
		[165]	0 '\0'	char
		[166]	66 'B'	char
		[167]	36 '$'	char
		[168]	105 'i'	char
		[169]	16 '\x10'	char
		[170]	0 '\0'	char
		[171]	-40 'Ř'	char
		[172]	0 '\0'	char
		[173]	0 '\0'	char
		[174]	0 '\0'	char
		[175]	66 'B'	char
		[176]	-116 'Ś'	char
		[177]	4 '\x4'	char
		[178]	0 '\0'	char
		[179]	66 'B'	char
		[180]	-116 'Ś'	char
		[181]	4 '\x4'	char
		[182]	0 '\0'	char
		[183]	66 'B'	char
		[184]	-116 'Ś'	char
		[185]	0 '\0'	char
		[186]	0 '\0'	char
		[187]	67 'C'	char
		[188]	-112 ''	char
		[189]	-45 'Ó'	char
		[190]	16 '\x10'	char
		[191]	0 '\0'	char
		[192]	-24 'č'	char
		[193]	0 '\0'	char
		[194]	3 '\x3'	char
		[195]	0 '\0'	char
		[196]	2 '\x2'	char
		[197]	36 '$'	char
		[198]	4 '\x4'	char
		[199]	0 '\0'	char
		[200]	98 'b'	char
		[201]	20 '\x14'	char
		[202]	0 '\0'	char
		[203]	0 '\0'	char
		[204]	0 '\0'	char
		[205]	0 '\0'	char
		[206]	52 '4'	char
		[207]	0 '\0'	char
		[208]	-62 'Â'	char
		[209]	-113 'Ź'	char
		[210]	-32 'ŕ'	char
		[211]	16 '\x10'	char
		[212]	0 '\0'	char
		[213]	-8 'ř'	char
		[214]	0 '\0'	char
		[215]	3 '\x3'	char
		[216]	0 '\0'	char
		[217]	3 '\x3'	char
		[218]	36 '$'	char
		[219]	1 '\x1'	char
		[220]	0 '\0'	char
		[221]	67 'C'	char
		[222]	-96 ' '	char
		[223]	52 '4'	char
		[224]	0 '\0'	char
		[225]	-62 'Â'	char
		[226]	-113 'Ź'	char
		[227]	2 '\x2'	char
		[228]	0 '\0'	char
		[229]	67 'C'	char
		[230]	-112 ''	char
		[231]	-112 ''	char
		[232]	-121 '‡'	char 0x87
		[233]	-60 'Ä'	char    0xC4

 */
        for (byte[] b: TestBuffer01) {
            ArrayList<Byte> TestList = new MakeByteArray().MakeArray(b);
            short CRCVal;
            CRCCalc crcCalc = new CRCCalc();
            CRCVal = crcCalc.getCRCValue(TestList);

            System.out.printf("CRC Value = %02x\n\r", CRCVal);
        }





    }

    @Test
    void APP_CalculateCrc() {
    }
}