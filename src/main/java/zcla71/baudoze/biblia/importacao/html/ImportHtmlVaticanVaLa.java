package zcla71.baudoze.biblia.importacao.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zcla71.baudoze.BauDoZeProperties.PropBiblia.PropImportacao.PropHtmlImporta;
import zcla71.baudoze.biblia.entity.Biblia;
import zcla71.baudoze.biblia.entity.Capitulo;
import zcla71.baudoze.biblia.entity.Livro;
import zcla71.baudoze.biblia.entity.Versiculo;
import zcla71.baudoze.biblia.service.BibliaService;

@RequiredArgsConstructor
@Component
@Slf4j
public class ImportHtmlVaticanVaLa extends ImportHtml {
	final private BibliaService bibliaService;

	final private String TXT_ESTRUTURA = """
Liber Genesis (Gen)
	1	1-31
	2	1-25
	3	1-24
	4	1-26
	5	1-32
	6	1-22
	7	1-24
	8	1-22
	9	1-29
	10	1-32
	11	1-32
	12	1-20
	13	1-18
	14	1-24
	15	1-21
	16	1-16
	17	1-27
	18	1-33
	19	1-38
	20	1-18
	21	1-34
	22	1-24
	23	1-20
	24	1-67
	25	1-34
	26	1-35
	27	1-46
	28	1-22
	29	1-35
	30	1-43
	31	1-54
	32	1-33
	33	1-20
	34	1-31
	35	1-29
	36	1-43
	37	1-36
	38	1-30
	39	1-23
	40	1-23
	41	1-57
	42	1-38
	43	1-34
	44	1-34
	45	1-28
	46	1-34
	47	1-31
	48	1-22
	49	1-32
	50	1-26
Liber Exodus (Ex)
	1	1-22
	2	1-25
	3	1-22
	4	1-31
	5	1-23
	6	1-30
	7	1-29
	8	1-28
	9	1-35
	10	1-29
	11	1-10
	12	1-51
	13	1-22
	14	1-31
	15	1-27
	16	1-36
	17	1-16
	18	1-27
	19	1-25
	20	1-26
	21	1-37
	22	1-30
	23	1-33
	24	1-18
	25	1-40
	26	1-37
	27	1-21
	28	1-43
	29	1-46
	30	1-38
	31	1-18
	32	1-35
	33	1-23
	34	1-35
	35	1-35
	36	1-38
	37	1-29
	38	1-31
	39	1-43
	40	1-38
Liber Leviticus (Lev)
	1	1-17
	2	1-16
	3	1-17
	4	1-35
	5	1-26
	6	1-23
	7	1-38
	8	1-36
	9	1-24
	10	1-20
	11	1-47
	12	1-8
	13	1-59
	14	1-57
	15	1-33
	16	1-34
	17	1-16
	18	1-30
	19	1-37
	20	1-27
	21	1-24
	22	1-33
	23	1-44
	24	1-23
	25	1-55
	26	1-46
	27	1-34
Liber Numeri (Num)
	1	1-54
	2	1-34
	3	1-51
	4	1-49
	5	1-31
	6	1-27
	7	1-89
	8	1-26
	9	1-23
	10	1-36
	11	1-35
	12	1-16
	13	1-33
	14	1-45
	15	1-41
	16	1-35
	17	1-28
	18	1-32
	19	1-22
	20	1-29
	21	1-35
	22	1-41
	23	1-30
	24	1-24
	25	1-18
	26	1-65
	27	1-23
	28	1-31
	29	1-39
	30	1-17
	31	1-54
	32	1-42
	33	1-56
	34	1-29
	35	1-34
	36	1-13
Liber Deuteronomii (Deut)
	1	1-46
	2	1-37
	3	1-29
	4	1-49
	5	1-33
	6	1-25
	7	1-26
	8	1-20
	9	1-29
	10	1-22
	11	1-32
	12	1-31
	13	1-19
	14	1-29
	15	1-23
	16	1-22
	17	1-20
	18	1-22
	19	1-21
	20	1-20
	21	1-23
	22	1-29
	23	1-26
	24	1-22
	25	1-19
	26	1-19
	27	1-26
	28	1-69
	29	1-28
	30	1-20
	31	1-30
	32	1-52
	33	1-29
	34	1-12
Liber Iosue (Ios)
	1	1-18
	2	1-24
	3	1-17
	4	1-25
	5	1-16
	6	1-27
	7	1-26
	8	1-35
	9	1-27
	10	1-43
	11	1-23
	12	1-24
	13	1-33
	14	1-15
	15	1-63
	16	1-10
	17	1-18
	18	1-28
	19	1-51
	20	1-9
	21	1-45
	22	1-34
	23	1-16
	24	1-33
Liber Iudicum (Iudic)
	1	1-36
	2	1-23
	3	1-31
	4	1-24
	5	1-32
	6	1-40
	7	1-25
	8	1-35
	9	1-57
	10	1-18
	11	1-40
	12	1-15
	13	1-25
	14	1-20
	15	1-20
	16	1-31
	17	1-13
	18	1-31
	19	1-30
	20	1-48
	21	1-25
Liber Ruth (Rut)
	1	1-22
	2	1-23
	3	1-18
	4	1-22
Liber I Samuelis (1Sam)
	1	1-28
	2	1-36
	3	1-21
	4	1-22
	5	1-12
	6	1-21
	7	1-16
	8	1-22
	9	1-27
	10	1-27
	11	1-15
	12	1-25
	13	1-23
	14	1-52
	15	1-35
	16	1-23
	17	1-58
	18	1-30
	19	1-24
	20	1-42
	21	1-16
	22	1-23
	23	1-28
	24	1-23
	25	1-44
	26	1-25
	27	1-12
	28	1-25
	29	1-11
	30	1-31
	31	1-13
Liber II Samuelis (2Sam)
	1	1-27
	2	1-32
	3	1-39
	4	1-12
	5	1-25
	6	1-23
	7	1-29
	8	1-18
	9	1-13
	10	1-19
	11	1-27
	12	1-31
	13	1-39
	14	1-33
	15	1-37
	16	1-23
	17	1-29
	18	1-32
	19	1-44
	20	1-26
	21	1-22
	22	1-51
	23	1-39
	24	1-25
Liber I Regum (1Reg)
	1	1-53
	2	1-46
	3	1-28
	4	1-20
	5	1-32
	6	1-38
	7	1-51
	8	1-66
	9	1-28
	10	1-29
	11	1-43
	12	1-33
	13	1-34
	14	1-31
	15	1-34
	16	1-34
	17	1-24
	18	1-46
	19	1-21
	20	1-43
	21	1-29
	22	1-54
Liber II Regum (2Reg)
	1	1-18
	2	1-25
	3	1-27
	4	1-44
	5	1-27
	6	1-33
	7	1-20
	8	1-29
	9	1-37
	10	1-36
	11	1-20
	12	1-22
	13	1-25
	14	1-29
	15	1-38
	16	1-20
	17	1-41
	18	1-37
	19	1-37
	20	1-21
	21	1-26
	22	1-20
	23	1-37
	24	1-20
	25	1-30
Liber I Paralipomenon (1Chr)
	1	1-54
	2	1-55
	3	1-24
	4	1-17 18b 18a 19-43
	5	1-41
	6	1-66
	7	1-40
	8	1-40
	9	1-44
	10	1-14
	11	1-47
	12	1-41
	13	1-14
	14	1-17
	15	1-29
	16	1-43
	17	1-27
	18	1-17
	19	1-19
	20	1-7
	21	1-30
	22	1-19
	23	1-32
	24	1-31
	25	1-31
	26	1-32
	27	1-34
	28	1-21
	29	1-30
Liber II Paralipomenon (2Chr)
	1	1-18
	2	1-17
	3	1-17
	4	1-22
	5	1-14
	6	1-42
	7	1-22
	8	1-18
	9	1-31
	10	1-19
	11	1-23
	12	1-16
	13	1-23
	14	1-14
	15	1-19
	16	1-14
	17	1-19
	18	1-34
	19	1-11
	20	1-37
	21	1-20
	22	1-12
	23	1-21
	24	1-27
	25	1-28
	26	1-23
	27	1-9
	28	1-27
	29	1-36
	30	1-27
	31	1-21
	32	1-33
	33	1-25
	34	1-33
	35	1-27
	36	1-23
Liber Esdrae (Esd)
	1	1-11
	2	1-70
	3	1-13
	4	1-24
	5	1-17
	6	1-22
	7	1-28
	8	1-36
	9	1-15
	10	1-44
Liber Nehemiae (Neh)
	1	1-11
	2	1-20
	3	1-38
	4	1-17
	5	1-19
	6	1-19
	7	1-72
	8	1-18
	9	1-37
	10	1-40
	11	1-36
	12	1-47
	13	1-31
Liber Thobis (Tob)
	1	1-22
	2	1-14
	3	1-17
	4	1-21
	5	1-22
	6	1-18
	7	1-17
	8	1-21
	9	1-6
	10	1-13
	11	1-18
	12	1-22
	13	1-18
	14	1-15
Liber Iudith (Iudt)
	1	1-16
	2	1-28
	3	1-10
	4	1-15
	5	1-24
	6	1-21
	7	1-32
	8	1-36
	9	1-14
	10	1-23
	11	1-23
	12	1-20
	13	1-20
	14	1-19
	15	1-14
	16	1-25
Liber Esther (Est)
	1	1a-1k 1-22
	2	1-23
	3	1-13 13a-13g 14-15 15a-15i
	4	1-8 8a 9-17 17a-17kk
	5	1-2 2a-2p 3-14
	6	1-14
	7	1-10
	8	1-12 12a-12cc 13-17
	9	1-19 19a 20-32
	10	1-3 3a-3k
Liber Iob (Iob)
	1	1-22
	2	1-13
	3	1-26
	4	1-21
	5	1-27
	6	1-30
	7	1-21
	8	1-22
	9	1-35
	10	1-22
	11	1-20
	12	1-25
	13	1-28
	14	1-22
	15	1-35
	16	1-22
	17	1-16
	18	1-21
	19	1-29
	20	1-29
	21	1-34
	22	1-30
	23	1-17
	24	1-25
	25	1-6
	26	1-14
	27	1-23
	28	1-28
	29	1-25
	30	1-31
	31	1-40
	32	1-22
	33	1-33
	34	1-37
	35	1-16
	36	1-33
	37	1-24
	38	1-41
	39	1-30
	40	1-32
	41	1-26
	42	1-16
Liber Psalmorum (Ps)
	1	1-6
	2	1-12
	3	1-9
	4	1-9
	5	1-13
	6	1-11
	7	1-18
	8	1-10
	9	1-21
	10	1-18
	11	1-7
	12	1-8
	13	1-6
	14	1-7
	15	1-5
	16	1-11
	17	1-15
	18	1-51
	19	1-15
	20	1-10
	21	1-14
	22	1-32
	23	1-6
	24	1-10
	25	1-22
	26	1-12
	27	1-14
	28	1-9
	29	1-11
	30	1-13
	31	1-25
	32	1-11
	33	1-22
	34	1-23
	35	1-28
	36	1-13
	37	1-40
	38	1-23
	39	1-14
	40	1-18
	41	1-14
	42	1-12
	43	1-5
	44	1-26
	45	1-18
	46	1-12
	47	1-10
	48	1-15
	49	1-21
	50	1-23
	51	1-21
	52	1-11
	53	1-7
	54	1-9
	55	1-24
	56	1-14
	57	1-12
	58	1-12
	59	1-18
	60	1-13
	61	1-9
	62	1-13
	63	1-12
	64	1-11
	65	1-14
	66	1-20
	67	1-8
	68	1-36
	69	1-37
	70	1-6
	71	1-24
	72	1-19
	73	1-28
	74	1-23
	75	1-11
	76	1-13
	77	1-21
	78	1-72
	79	1-13
	80	1-20
	81	1-17
	82	1-8
	83	1-19
	84	1-13
	85	1-14
	86	1-17
	87	1-7
	88	1-19
	89	1-53
	90	1-17
	91	1-16
	92	1-16
	93	1-5
	94	1-24
	95	1-11
	96	1-13
	97	1-12
	98	1-9
	99	1-9
	100	1-5
	101	1-8
	102	1-29
	103	1-22
	104	1-35
	105	1-45
	106	1-48
	107	1-43
	108	1-14
	109	1-31
	110	1-7
	111	1-10
	112	1-10
	113	1-9
	114	1-8
	115	1-18
	116	1-19
	117	1-2
	118	1-29
	119	1-176
	120	1-7
	121	1-8
	122	1-9
	123	1-4
	124	1-8
	125	1-5
	126	1-6
	127	1-5
	128	1-6
	129	1-8
	130	1-8
	131	1-3
	132	1-18
	133	1-3
	134	1-3
	135	1-21
	136	1-26
	137	1-9
	138	1-8
	139	1-24
	140	1-14
	141	1-10
	142	1-8
	143	1-12
	144	1-15
	145	1-21
	146	1-10
	147	1-20
	148	1-14
	149	1-9
	150	1-5
Liber Proverbiorum (Prov)
	1	1-33
	2	1-22
	3	1-35
	4	1-27
	5	1-23
	6	1-35
	7	1-27
	8	1-36
	9	1-18
	10	1-32
	11	1-31
	12	1-28
	13	1-25
	14	1-35
	15	1-33
	16	1-33
	17	1-28
	18	1-24
	19	1-29
	20	1-30
	21	1-31
	22	1-29
	23	1-35
	24	1-34
	25	1-28
	26	1-28
	27	1-27
	28	1-28
	29	1-27
	30	1-33
	31	1-31
Liber Ecclesiastes (Qoh)
	1	1-18
	2	1-26
	3	1-22
	4	1-17
	5	1-19
	6	1-12
	7	1-29
	8	1-17
	9	1-18
	10	1-20
	11	1-10
	12	1-14
Canticum Canticorum (Cant)
	1	1-17
	2	1-17
	3	1-11
	4	1-16
	5	1-16
	6	1-12
	7	1-14
	8	1-14
Liber Sapientiae (Sap)
	1	1-16
	2	1-24
	3	1-19
	4	1-20
	5	1-23
	6	1-25
	7	1-30
	8	1-21
	9	1-18
	10	1-21
	11	1-26
	12	1-27
	13	1-19
	14	1-31
	15	1-19
	16	1-29
	17	1-20
	18	1-25
	19	1-22
Liber Ecclesiasticus (Sir)
	PROLOGUS	-
	1	1-18 (19) 20-40
	2	1-23
	3	1-34
	4	1-36
	5	1-17
	6	1-37
	7	1-40
	8	1-22
	9	1-9 (10) (11) 12-25
	10	1-34
	11	1-36
	12	1-19
	13	1-32
	14	1-27
	15	1-22
	16	1-31
	17	1-31
	18	1-33
	19	1-5 (6) 7-28
	20	1-33
	21	1-31
	22	1-22 (23) 24-33
	23	1-38
	24	1-47
	25	1-36
	26	1-28
	27	1-2 (3) 4-33
	28	1-30
	29	1-15 (16) (17) 18-20 (21) 22 (23) 24-35
	30	1-27
	31	1-42
	32	1-22 (23) 24-28
	33	1-33
	34	1-10 (11) 12-31
	35	1-2 (3) 4-26
	36	1-28
	37	1-34
	38	1-39
	39	1-41
	40	1-32
	41	1-28
	42	1-26
	43	1-32 (33) 34-37
	44	1-27
	45	1-31
	46	1-23
	47	1-31
	48	1-28
	49	1-19
	50	1-31
	51	1-38
Liber Isaiae (Is)
	1	1-31
	2	1-22
	3	1-26
	4	1-6
	5	1-30
	6	1-13
	7	1-25
	8	1-23
	9	1-20
	10	1-34
	11	1-16
	12	1-6
	13	1-22
	14	1-32
	15	1-9
	16	1-14
	17	1-14
	18	1-7
	19	1-25
	20	1-6
	21	1-17
	22	1-25
	23	1-18
	24	1-23
	25	1-12
	26	1-21
	27	1-13
	28	1-29
	29	1-24
	30	1-33
	31	1-9
	32	1-20
	33	1-24
	34	1-17
	35	1-10
	36	1-22
	37	1-38
	38	1-22
	39	1-8
	40	1-31
	41	1-29
	42	1-25
	43	1-28
	44	1-28
	45	1-25
	46	1-13
	47	1-15
	48	1-22
	49	1-26
	50	1-11
	51	1-23
	52	1-15
	53	1-12
	54	1-17
	55	1-13
	56	1-12
	57	1-21
	58	1-14
	59	1-21
	60	1-22
	61	1-11
	62	1-12
	63	1-19
	64	1-11
	65	1-25
	66	1-24
Liber Ieremiae (Ier)
	1	1-19
	2	1-37
	3	1-25
	4	1-31
	5	1-31
	6	1-30
	7	1-34
	8	1-23
	9	1-25
	10	1-25
	11	1-23
	12	1-17
	13	1-27
	14	1-22
	15	1-21
	16	1-21
	17	1-27
	18	1-23
	19	1-15
	20	1-18
	21	1-14
	22	1-30
	23	1-40
	24	1-10
	25	1-38
	26	1-24
	27	1-22
	28	1-17
	29	1-32
	30	1-24
	31	1-40
	32	1-44
	33	1-26
	34	1-22
	35	1-19
	36	1-32
	37	1-21
	38	1-28
	39	1-18
	40	1-16
	41	1-18
	42	1-22
	43	1-13
	44	1-30
	45	1-5
	46	1-28
	47	1-7
	48	1-47
	49	1-39
	50	1-46
	51	1-64
	52	1-34
Lamentationes (Lam)
	1	1-22
	2	1-22
	3	1-66
	4	1-22
	5	1-22
Liber Baruch (Bar)
	1	1-22
	2	1-35
	3	1-38
	4	1-37
	5	1-9
	6	0 1-72
Prophetia Ezechielis (Ez)
	1	1-28
	2	1-9
	3	1-27
	4	1-17
	5	1-17
	6	1-14
	7	1-27
	8	1-18
	9	1-11
	10	1-22
	11	1-25
	12	1-28
	13	1-23
	14	1-23
	15	1-8
	16	1-63
	17	1-24
	18	1-32
	19	1-14
	20	1-44
	21	1-37
	22	1-31
	23	1-49
	24	1-27
	25	1-17
	26	1-21
	27	1-36
	28	1-26
	29	1-21
	30	1-26
	31	1-18
	32	1-32
	33	1-33
	34	1-31
	35	1-15
	36	1-38
	37	1-28
	38	1-23
	39	1-29
	40	1-41 42a 43a 42b 43b 44-49
	41	1-26
	42	1-20
	43	1-27
	44	1-31
	45	1-25
	46	1-24
	47	1-23
	48	1-35
Prophetia Danielis (Dan)
	1	1-21
	2	1-49
	3	1-100
	4	1-34
	5	1-30
	6	1-29
	7	1-28
	8	1-27
	9	1-27
	10	1-21
	11	1-45
	12	1-13
	13	1-64
	14	1-42
Prophetia Osee (Os)
	1	1-9
	2	1-26
	3	1-5
	4	1-19
	5	1-15
	6	1-11
	7	1-16
	8	1-14
	9	1-17
	10	1-15
	11	1-11
	12	1-15
	13	1-15
	14	1-10
Prophetia Ioel (Ioel)
	1	1-20
	2	1-27
	3	1-5
	4	1-21
Prophetia Amos (Am)
	1	1-15
	2	1-16
	3	1-15
	4	1-13
	5	1-27
	6	1-14
	7	1-17
	8	1-14
	9	1-15
Prophetia Abdiae (Abd)
	0	1-21
Prophetia Ionae (Ion)
	1	1-16
	2	1-11
	3	1-10
	4	1-11
Prophetia Michaeae (Mic)
	1	1-16
	2	1-13
	3	1-12
	4	1-14
	5	1-14
	6	1-16
	7	1-20
Prophetia Nahum (Nah)
	1	1-14
	2	1-14
	3	1-19
Prophetia Habacuc (Hab)
	1	1-17
	2	1-20
	3	1-19
Prophetia Sophoniae (Soph)
	1	1-18
	2	1-15
	3	1-20
Prophetia Aggaei (Ag)
	1	1-15
	2	1-23
Prophetia Zachariae (Zac)
	1	1-17
	2	1-17
	3	1-10
	4	1-14
	5	1-11
	6	1-15
	7	1-14
	8	1-23
	9	1-17
	10	1-12
	11	1-17
	12	1-14
	13	1-9
	14	1-21
Prophetia Malachiae (Mal)
	1	1-14
	2	1-17
	3	1-24
Liber I Maccabaeorum (1Mac)
	1	1-64
	2	1-70
	3	1-60
	4	1-61
	5	1-68
	6	1-63
	7	1-50
	8	1-32
	9	1-73
	10	1-89
	11	1-74
	12	1-54
	13	1-54
	14	1-49
	15	1-41
	16	1-24
Liber II Maccabaeorum (2Mac)
	1	1-36
	2	1-32
	3	1-40
	4	1-50
	5	1-27
	6	1-31
	7	1-42
	8	1-36
	9	1-29
	10	1-38
	11	1-38
	12	1-46
	13	1-26
	14	1-46
	15	1-39
Evangelium secundum Matthaeum (Mt)
	1	1-25
	2	1-23
	3	1-17
	4	1-25
	5	1-48
	6	1-34
	7	1-29
	8	1-34
	9	1-38
	10	1-42
	11	1-30
	12	1-50
	13	1-58
	14	1-36
	15	1-39
	16	1-28
	17	1-20 (21) 22-27
	18	1-10 (11) 12-35
	19	1-30
	20	1-34
	21	1-46
	22	1-46
	23	1-13 (14) 15-39
	24	1-51
	25	1-46
	26	1-75
	27	1-66
	28	1-20
Evangelium secundum Marcum (Mc)
	1	1-45
	2	1-28
	3	1-35
	4	1-41
	5	1-43
	6	1-56
	7	1-15 (16) 17-37
	8	1-38
	9	1-43 (44) 45 (46) 47-50
	10	1-52
	11	1-25 (26) 27-33
	12	1-44
	13	1-37
	14	1-72
	15	1-27 (28) 29-47
	16	1-20
Evangelium secundum Lucam (Lc)
	1	1-80
	2	1-52
	3	1-38
	4	1-44
	5	1-39
	6	1-49
	7	1-50
	8	1-56
	9	1-62
	10	1-42
	11	1-54
	12	1-59
	13	1-35
	14	1-35
	15	1-32
	16	1-31
	17	1-35 (36) 37
	18	1-43
	19	1-48
	20	1-47
	21	1-38
	22	1-71
	23	1-16 (17) 18-56
	24	1-53
Evangelium secundum Ioannem (Io)
	1	1-51
	2	1-25
	3	1-36
	4	1-54
	5	1-3 (4) 5-47
	6	1-71
	7	1-53
	8	1-59
	9	1-41
	10	1-42
	11	1-57
	12	1-50
	13	1-38
	14	1-31
	15	1-27
	16	1-33
	17	1-26
	18	1-40
	19	1-42
	20	1-31
	21	1-25
Actus Apostolorum (Act)
	1	1-26
	2	1-47
	3	1-26
	4	1-37
	5	1-42
	6	1-15
	7	1-60
	8	1-36 (37) 38-40
	9	1-43
	10	1-48
	11	1-30
	12	1-25
	13	1-52
	14	1-28
	15	1-33 (34) 35-41
	16	1-40
	17	1-34
	18	1-28
	19	1-40
	20	1-38
	21	1-40
	22	1-30
	23	1-35
	24	1-6 (7) 8-27
	25	1-27
	26	1-32
	27	1-44
	28	1-28 (29) 30-31
Epistula ad Romanos (Rom)
	1	1-32
	2	1-29
	3	1-31
	4	1-25
	5	1-21
	6	1-23
	7	1-25
	8	1-39
	9	1-33
	10	1-21
	11	1-36
	12	1-21
	13	1-14
	14	1-23
	15	1-33
	16	1-23 (24) 25-27
Epistula I ad Corinthios (1Cor)
	1	1-31
	2	1-16
	3	1-23
	4	1-21
	5	1-13
	6	1-20
	7	1-40
	8	1-13
	9	1-27
	10	1-33
	11	1-34
	12	1-31
	13	1-13
	14	1-40
	15	1-58
	16	1-24
Epistula II ad Corinthios (2Cor)
	1	1-24
	2	1-17
	3	1-18
	4	1-18
	5	1-21
	6	1-18
	7	1-16
	8	1-24
	9	1-15
	10	1-18
	11	1-33
	12	1-21
	13	1-13
Epistula ad Galatas (Gal)
	1	1-24
	2	1-21
	3	1-29
	4	1-31
	5	1-26
	6	1-18
Epistula ad Ephesios (Eph)
	1	1-23
	2	1-22
	3	1-21
	4	1-32
	5	1-33
	6	1-24
Epistula ad Philippenses (Phil)
	1	1-30
	2	1-30
	3	1-21
	4	1-23
Epistula ad Colossenses (Col)
	1	1-29
	2	1-23
	3	1-25
	4	1-18
Epistula I ad Thessalonicenses (1Th)
	1	1-10
	2	1-20
	3	1-13
	4	1-18
	5	1-28
Epistula II ad Thessalonicenses (2Th)
	1	1-12
	2	1-17
	3	1-18
Epistula I ad Timotheum (1Tim)
	1	1-20
	2	1-15
	3	1-16
	4	1-16
	5	1-25
	6	1-21
Epistula II ad Timotheum (2Tim)
	1	1-18
	2	1-26
	3	1-17
	4	1-22
Epistula ad Titum (Tit)
	1	1-16
	2	1-15
	3	1-15
Epistulam ad Philemonem (Phm)
	0	1-25
Epistula ad Hebraeos (Hebr)
	1	1-14
	2	1-18
	3	1-19
	4	1-16
	5	1-14
	6	1-20
	7	1-28
	8	1-13
	9	1-28
	10	1-39
	11	1-40
	12	1-29
	13	1-25
Epistula Iacobi (Iac)
	1	1-27
	2	1-26
	3	1-18
	4	1-17
	5	1-20
Epistula I Petri (1Petr)
	1	1-25
	2	1-25
	3	1-22
	4	1-19
	5	1-14
Epistula II Petri (2Petr)
	1	1-21
	2	1-22
	3	1-18
Epistula I Ioannis (1Io)
	1	1-10
	2	1-29
	3	1-24
	4	1-21
	5	1-21
Epistula II Ioannis (2Io)
	0	1-13
Epistula III Ioannis (3Io)
	0	1-15
Epistula Iudae (Iud)
	0	1-25
Apocalypsis Ioannis (Ap)
	1	1-20
	2	1-29
	3	1-22
	4	1-11
	5	1-14
	6	1-17
	7	1-17
	8	1-13
	9	1-21
	10	1-11
	11	1-19
	12	1-18
	13	1-18
	14	1-20
	15	1-8
	16	1-21
	17	1-18
	18	1-24
	19	1-21
	20	1-15
	21	1-27
	22	1-21
			""";

	private class IBiblia {
		private List<ILivro> livros;

		private ILivro getLivroByNome(String nome) {
			return biblia.livros.stream().filter(l -> nome.equals(l.nome)).findFirst().orElseThrow();
		}

		private ILivro getLivroBySigla(String sigla) {
			return biblia.livros.stream().filter(l -> sigla.equals(l.sigla)).findFirst().orElseThrow();
		}

		private ILivro getProximoLivro(ILivro livro) {
			int index = biblia.livros.indexOf(livro) + 1;
			if (index < biblia.livros.size()) {
				return biblia.livros.get(index);
			}
			return null;
		}
	}

	private class ILivro {
		private String nome;
		private String sigla;
		private List<ICapitulo> capitulos;

		public ICapitulo proximoCapitulo(ICapitulo capitulo) {
			int index = capitulos.indexOf(capitulo) + 1;
			if (index < capitulos.size()) {
				return capitulos.get(index);
			}
			return null;
		}
	}

	private class ICapitulo {
		private String numero;
		private String estrutura;
		private List<String> versiculos;

		public String proximoVersiculo(String versiculo) {
			int index = versiculos.indexOf(versiculo) + 1;
			if (index < versiculos.size()) {
				return versiculos.get(index);
			}
			return null;
		}
	}

	private IBiblia biblia;

	private void montaEstrutura() {
		biblia = new IBiblia();
		biblia.livros = new ArrayList<>();
		String[] linhas = TXT_ESTRUTURA.split("\n");
		ILivro livro = null;
		for (String linha : linhas) {
			if (linha.startsWith("\t")) {
				// Novo capítulo
				ICapitulo capitulo = new ICapitulo();
				capitulo.estrutura = linha.trim();
				capitulo.versiculos = new ArrayList<>();
				String[] trechos = capitulo.estrutura.split("\\s");
				capitulo.numero = trechos[0];
				trechos = Arrays.copyOfRange(trechos, 1, trechos.length);
				for (String trecho : trechos) {
					String[] partes = trecho.split("-");
					if (partes.length == 2) {
						String ini = partes[0];
						String fim = partes[1];
						String versiculo = ini;
						capitulo.versiculos.add(versiculo);
						while (!versiculo.equals(fim)) {
							versiculo = proximoVersiculo(versiculo);
							capitulo.versiculos.add(versiculo);
						}
					} else {
						capitulo.versiculos.add(trecho);
					}
				}
				livro.capitulos.add(capitulo);
			} else {
				// Novo livro
				Pattern pattern = Pattern.compile("(.+)\\((.+)\\)");
				Matcher matcher = pattern.matcher(linha);
				if (matcher.find()) {
					livro = new ILivro();
					livro.nome = matcher.group(1).trim();
					livro.sigla = matcher.group(2);
					livro.capitulos = new ArrayList<>();
					biblia.livros.add(livro);
				} else {
					throw new RuntimeException("Novo livro: regex falhou");
				}
			}
		}
	}

	private String proximoVersiculo(String versiculo) {
		final List<String> SEQUENCIA_LETRAS = Arrays.asList("a b c d e f g h i k l m n o p q r s t u v x y z aa bb cc dd ee ff gg hh ii kk".split(" ")); // não usa nem j nem w
		if (versiculo.matches("\\d+")) {
			int intVersiculo = Integer.parseInt(versiculo);
			return String.valueOf(intVersiculo + 1);
		} else if (versiculo.matches("\\d+[a-z]+")) {
			Pattern pattern = Pattern.compile("(\\d+)([a-z]+)");
			Matcher matcher = pattern.matcher(versiculo);
			if (matcher.find()) {
				String numero = matcher.group(1);
				String letra = matcher.group(2);
				return numero + SEQUENCIA_LETRAS.get(SEQUENCIA_LETRAS.indexOf(letra) + 1);
			} else {
				throw new RuntimeException("Isso não poderia acontecer");
			}
		} else {
			throw new RuntimeException("Isso não deveria acontecer");
		}
	}

	public void htmlImporta(PropHtmlImporta phi) throws IOException, InterruptedException {
		log.info("htmlImporta(\"" + phi.getCodigo() + "\")");

		montaEstrutura();

		if (this.bibliaService.buscaBibliaPorCodigo(phi.getCodigo()) == null) {
			Biblia biblia = fromHtml(phi);
			if (biblia != null) {
				this.bibliaService.incluir(biblia);
				// TODO tabela novo_testamento
			}
		}
	}

	private Biblia fromHtml(PropHtmlImporta phi) throws IOException, InterruptedException {
		log.info("fromHtml(\"" + phi.getCodigo() + "\")");

		// Bíblia
		Biblia result = newBiblia(phi);

		// Testamentos
		Document docBiblia = getDocument(phi.getUri());
		Elements linksTestamentos = docBiblia.select("div#corpo table table a[href~=^.+testamentum_lt\\.html$]");
		List<ILivro> primeirosLivros = new ArrayList<>();
		primeirosLivros.add(biblia.livros.get(0));
		primeirosLivros.add(biblia.getLivroBySigla("Mt"));
		for (Element linkTestamento : linksTestamentos) {
			ILivro iLivro = primeirosLivros.remove(0);
			result.getLivros().addAll(fromTestamento(linkTestamento.absUrl("href"), iLivro));
		}
		result.getLivros().forEach(l -> l.setBiblia(result));

		// Fim
		return result;
	}

	private List<Livro> fromTestamento(String url, ILivro iLivro) throws IOException, InterruptedException {
		log.info("fromTestamento(\"" + url + "\")");

		// Testamento
		List<Livro> result = new ArrayList<>();

		// Livros
		Document docTestamento = getDocument(url);
		Elements linksLivros = docTestamento.select("div#corpo table table a[href~=^nova-vulgata_.+_lt\\.html$]");
		for (Element linkLivro : linksLivros) {
			result.add(fromLivro(linkLivro.absUrl("href"), linkLivro.text(), iLivro));
			iLivro = biblia.getProximoLivro(iLivro);
		}

		return result;
	}

	private Livro fromLivro(String url, String nome, ILivro iLivro) throws IOException, InterruptedException {
		log.info("fromTestamento(\"" + url + "\")");

		// Livro
		if (!nome.equals(iLivro.nome)) {
			throw new RuntimeException("Livro fora de ordem");
		}
		Livro result = new Livro();
		// result.setBiblia(); // em fromHtml()
		result.setSigla(iLivro.sigla);
		result.setNome(nome);
		result.setCapitulos(new ArrayList<>());
		Document docLivro = getDocument(url);
		Elements pCapituloNumeros = docLivro.select("p a[name~=^(PSALMUS )?\\d+$]");

		// Abd, Phm, 2Io, 3Io, Iud: livros sem capítulos
		List<String> livrosSemCapitulos = List.of("Abd", "Phm", "2Io", "3Io", "Iud");
		if (livrosSemCapitulos.contains(result.getSigla())) {
			// Abd, Phm, 2Io
			pCapituloNumeros = docLivro.select("td td p:nth-last-child(2)");
			// 3Io
			if (pCapituloNumeros.isEmpty()) {
				pCapituloNumeros = docLivro.select("td td font p");
			}
			// Iud
			if (pCapituloNumeros.text().strip().length() == 0) {
				pCapituloNumeros = docLivro.select("td td p:nth-last-child(3)");
			}
		}

		ICapitulo iCapitulo = iLivro.capitulos.get(0);

		// Sir: livro com prólogo
		if ("PROLOGUS".equals(iCapitulo.numero)) {
			log.info("=> prologus");
			Capitulo capitulo = new Capitulo();
			capitulo.setLivro(result);
			capitulo.setNumero(iCapitulo.numero);
			capitulo.setVersiculos(new ArrayList<>());
			result.getCapitulos().add(capitulo);

			Elements texto = docLivro.select("p:nth-child(7)");
			Versiculo versiculo = new Versiculo();
			versiculo.setCapitulo(capitulo);
			versiculo.setNumero("");
			versiculo.setTexto(texto.text());
			capitulo.getVersiculos().add(versiculo);

			iCapitulo = iLivro.proximoCapitulo(iCapitulo);
		}

		for (Element pCapituloNumero : pCapituloNumeros) {
			Element pCapitulo = pCapituloNumero;
			List<String> capituloElementos = List.of("p");
			while (!capituloElementos.contains(pCapitulo.normalName())) {
				pCapitulo = pCapitulo.parentElement();
			}

			Element a = pCapitulo.select("a").first();
			String numCapitulo = "";
			if (a != null) {
				numCapitulo = pCapitulo.select("a").first().text();
			}
			if (numCapitulo.startsWith("PSALMUS ")) {
				numCapitulo = numCapitulo.substring(8);
			}

			if (!("".equals(numCapitulo) ? "0" : numCapitulo).equals(iCapitulo.numero)) {
				throw new RuntimeException("Capítulo fora de ordem");
			}
			Capitulo capitulo = new Capitulo();
			capitulo.setLivro(result);
			capitulo.setNumero(numCapitulo);
			capitulo.setVersiculos(new ArrayList<>());
			List<Node> children = pCapitulo.childNodes();
			Versiculo ultVersiculo = null;
			String proximoVersiculo = iCapitulo.versiculos.get(0);
			for (Node child : children) {
				if (child instanceof TextNode) {
					String texto = "0 DUMMY";
					if (child != null) {
						texto = child.nodeValue().replace('\u00A0', ' ').strip();
					}
					if (texto.length() > 0) {
						// ----- Erros conhecidos nas páginas -----

						// Num 1,1: falta o número do versículo
						if (result.getSigla().equals("Num") && capitulo.getNumero().equals("1") && capitulo.getVersiculos().size() == 0) {
							texto = "1 " + texto;
						}
						// Iudic 19,1: antes dele tem um texto que é continuação de 18,31
						if (result.getSigla().equals("Iudic") && capitulo.getNumero().equals("19") && capitulo.getVersiculos().size() == 0) {
							if (texto.startsWith("In ")) {
								Capitulo cap18 = result.getCapitulos().stream().filter(c -> c.getNumero().equals("18")).findFirst().get();
								Versiculo ver31 = cap18.getVersiculos().stream().filter(v -> v.getNumero().equals("31")).findFirst().get();
								ver31.setTexto(ver31.getTexto() + "\n" + texto);
								continue;
							}
						}
						// Ps 10-147: tira a referência à Vulgata
						if (result.getSigla().equals("Ps") && capitulo.getVersiculos().size() == 0) {
							if (texto.matches("^\\(.+\\)$")) {
								continue;
							}
						}
						// Bar 6: tem um versículo sem numeração; padronizei como "0".
						if (result.getSigla().equals("Bar") && capitulo.getNumero().equals("6") && capitulo.getVersiculos().size() == 0) {
							texto = "0 " + texto;
						}
						// Act 17,1: falta o espaço entre o número do versículo e o texto.
						if (result.getSigla().equals("Act") && capitulo.getNumero().equals("17") && capitulo.getVersiculos().size() == 0) {
							if (texto.startsWith("1Cum")) {
								texto = "1 " + texto.substring(1);
							}
						}

						String numVersiculo = texto.split(" ")[0];
						// 1Chr 4,18a.18b: estão com o número do versículo errado
						if (result.getSigla().equals("1Chr") && capitulo.getNumero().equals("4") && numVersiculo.equals("18a")) {
							numVersiculo = "18b";
						} else if (result.getSigla().equals("1Chr") && capitulo.getNumero().equals("4") && numVersiculo.equals("18b")) {
							numVersiculo = "18a";
						}
						// 1Chr 11,40: o versículo está como "0"
						if (result.getSigla().equals("1Chr") && capitulo.getNumero().equals("11") && numVersiculo.equals("0")) {
							numVersiculo = "40";
						}
						// Sir 1,20: o versículo está como "(20"
						if (result.getSigla().equals("Sir") && capitulo.getNumero().equals("1") && numVersiculo.equals("(20")) {
							numVersiculo = "20";
						}
						// Ez 31,2: o versículo está como "2“"
						if (result.getSigla().equals("Ez") && capitulo.getNumero().equals("31") && numVersiculo.equals("2“")) {
							numVersiculo = "2";
						}
						if (numVersiculo.matches("\\d+.?")) {
							while (!proximoVersiculo.equals(numVersiculo)) { // TODO Tratar os últimos versículos (Num 4, Est 4)
								// ----- Versículos "embutidos" no versículo anterior -----
								String regex = "[\\.|\\s|\\r|\\n]\\(?" + Pattern.quote(proximoVersiculo) + "\\)?\\s";
								Pattern pattern = Pattern.compile(regex);
								String regexInput = " " + ultVersiculo.getTexto() + " ";
								Matcher matcher = pattern.matcher(regexInput);
								if (matcher.find()) {
									log.info("=> consertando " + result.getSigla() + " " + capitulo.getNumero() + "," + proximoVersiculo);
									String[] spl = regexInput.split(regex);
									String textoEsse = "";
									String textoProximo = "";
									if (spl.length > 0) {
										textoEsse = spl[0].trim();
									}
									if (spl.length > 1) {
										textoProximo = spl[1];
									}
									ultVersiculo.setTexto(textoEsse);
									Versiculo versiculo = new Versiculo();
									versiculo.setCapitulo(capitulo);
									versiculo.setNumero(String.valueOf(proximoVersiculo));
									versiculo.setTexto(textoProximo.trim());
									capitulo.getVersiculos().add(versiculo);
									ultVersiculo = versiculo;
									proximoVersiculo = iCapitulo.proximoVersiculo(proximoVersiculo);
								} else {
									throw new RuntimeException("Isso não deveria acontecer");
								}
							}

							String textoVersiculo = texto.substring(numVersiculo.length()).strip();
							Versiculo versiculo = new Versiculo();
							versiculo.setCapitulo(capitulo);
							versiculo.setNumero(numVersiculo);
							versiculo.setTexto(textoVersiculo);
							capitulo.getVersiculos().add(versiculo);
							ultVersiculo = versiculo;

							proximoVersiculo = iCapitulo.proximoVersiculo(proximoVersiculo);
						} else {
							numVersiculo = Objects.requireNonNull(ultVersiculo).getNumero();
							String textoVersiculo = texto.strip();

							// Sir 9,9: separa os versículos ausentes "colados"
							if (result.getSigla().equals("Sir") && capitulo.getNumero().equals("9") && numVersiculo.equals("9")) {
								textoVersiculo = textoVersiculo.replace("(10. 11)", "(10) (11)");
							}

							// Sir 29,15: separa os versículos ausentes "colados"
							if (result.getSigla().equals("Sir") && capitulo.getNumero().equals("29") && numVersiculo.equals("15")) {
								textoVersiculo = textoVersiculo.replace("(16 17)", "(16) (17)");
							}

							Objects.requireNonNull(ultVersiculo).setTexto(ultVersiculo.getTexto() + "\n" + textoVersiculo);
						}
					}
				}
			}
			result.getCapitulos().add(capitulo);

			iCapitulo = iLivro.proximoCapitulo(iCapitulo);
		}
		return result;
	}

	private String proximoVersiculo(String siglaLivro, String numCapitulo, String numVersiculo) {
		ILivro livro = biblia.getLivroBySigla(siglaLivro);

		ICapitulo capitulo = livro.capitulos.stream().filter(c -> ("".equals(numCapitulo) ? "0" : numCapitulo).equals(c.numero)).findFirst().orElseThrow();
		int index = capitulo.versiculos.indexOf(numVersiculo) + 1;
		if (index >= capitulo.versiculos.size()) {
			return null;
		}
		return capitulo.versiculos.get(index);
	}
}
