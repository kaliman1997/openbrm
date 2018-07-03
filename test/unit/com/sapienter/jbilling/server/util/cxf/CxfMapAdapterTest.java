package com.sapienter.jbilling.server.util.cxf;

import com.sapienter.jbilling.server.metafields.MetaFieldValueWS;
import in.saralam.sbs.server.advancepricing.*;
import junit.framework.TestCase;
import in.saralam.sbs.server.PriceModel.*;

import java.util.*;

/**
 * @author Gerhard
 * @since 17/01/14
 */
public class CxfMapAdapterTest extends TestCase {

    public void testCxfMapMapListMetafieldAdapter() throws Exception {
        CxfMapMapListMetafieldAdapter a = new CxfMapMapListMetafieldAdapter();
        Map<Integer, Map<Date, List<MetaFieldValueWS>>> m = new HashMap<Integer, Map<Date, List<MetaFieldValueWS>>>();
        Date d = new Date();
        MetaFieldValueWS ws1 = new MetaFieldValueWS();
        ws1.setId(1);
        MetaFieldValueWS ws2 = new MetaFieldValueWS();
        ws2.setId(2);

        Map<Date, List<MetaFieldValueWS>> m2 = new HashMap<Date, List<MetaFieldValueWS>>();
        m2.put(d, Arrays.asList(ws1, ws2));
        m.put(1, m2);

        CxfMapMapListMetafield c = a.marshal(m);
        assertEquals(1, c.getEntries().size());
        CxfMapMapListMetafield.KeyValueEntry e = (CxfMapMapListMetafield.KeyValueEntry) c.getEntries().get(0);
        assertEquals(new Integer(1), e.key);
        assertEquals(1, e.value.entries.size());

        CxfMapDateListMetafield.KeyValueEntry e2 = e.value.entries.get(0);
        assertEquals(d, e2.getKey());
        assertEquals(2, e2.getValue().size());

        for(MetaFieldValueWS mf : e2.getValue()) {
            if(mf.getId() != 1) {
                assertEquals(2, mf.getId().intValue());
            } else {
                assertEquals(1, mf.getId().intValue());
            }
        }


        m = a.unmarshal(c);
        assertEquals(1, m.size());
        Map.Entry<Integer, Map<Date, List<MetaFieldValueWS>>> e3 = m.entrySet().iterator().next();
        assertEquals(new Integer(1), e3.getKey());
        Map<Date, List<MetaFieldValueWS>> m3 = e3.getValue();
        assertEquals(1, m3.size());
        Map.Entry<Date, List<MetaFieldValueWS>> e4 = m3.entrySet().iterator().next();
        assertEquals(d, e4.getKey());
        assertEquals(2, e4.getValue().size());

        for(MetaFieldValueWS mf : e4.getValue()) {
            if(mf.getId() != 1) {
                assertEquals(2, mf.getId().intValue());
            } else {
                assertEquals(1, mf.getId().intValue());
            }
        }
    }

    public void testCxfMapIntegerDateAdapter() throws Exception {
        CxfMapIntegerDateAdapter a = new CxfMapIntegerDateAdapter();

        Date d = new Date();
        Date d2 = new Date();
        Map<Integer, Date> m = new HashMap<Integer, Date>();
        m.put(1, d);
        m.put(2, d2);
        CxfMapIntegerDate id = a.marshal(m);
        assertEquals(2, id.getEntries().size());
        for(BaseCxfMap.KeyValueEntry e : id.getEntries()) {
            if(e.getKey().equals(new Integer(1))) {
                assertEquals(d, e.getValue());
            } else {
                assertEquals(d2, e.getValue());
                assertEquals(new Integer(2), e.getKey());

            }
        }

        Map<Integer, Date> m2 = a.unmarshal(id);
        for(Map.Entry<Integer,Date> e : m2.entrySet()) {
            if(e.getKey().equals(new Integer(1))) {
                assertEquals(d, e.getValue());
            } else {
                assertEquals(d2, e.getValue());
                assertEquals(new Integer(2), e.getKey());

            }
        }
    }

    public void testCxfMapDatePriceModel() throws Exception {
        CxfSMapDatePriceModelAdapter a = new CxfSMapDatePriceModelAdapter();

        Date d1 = new Date();
        PriceModelWS p1 = new PriceModelWS();
        p1.setId(1);
        Thread.sleep(1);
        Date d2 = new Date();
        PriceModelWS p2 = new PriceModelWS();
        p2.setId(2);
        Map<Date, PriceModelWS> m = new HashMap<Date, PriceModelWS>();
        m.put(d1, p1);
        m.put(d2, p2);
        CxfSMapDatePriceModel id = a.marshal(m);
        assertEquals(2, id.getEntries().size());
        Collections.sort(id.getEntries(), new Comparator<CxfSMapDatePriceModel.KeyValueEntry>() {
        	public int compare(CxfSMapDatePriceModel.KeyValueEntry entry1, CxfSMapDatePriceModel.KeyValueEntry entry2) {
        		return new Long(entry1.getKey().getTime()).compareTo(new Long(entry2.getKey().getTime()));
        	}
        });
        CxfSMapDatePriceModel.KeyValueEntry e = id.getEntries().get(0);
        System.out.println("Date d1: " + d1.getTime());
        System.out.println("Date d2: " + d2.getTime());
        System.out.println("Date e.getKey() d1: " + e.getKey().getTime());
        System.out.println("Date e.getKey() d2: " + id.getEntries().get(1).getKey().getTime());
        assertTrue(d1.getTime() == e.getKey().getTime());
        assertEquals(new Integer(1), e.getValue().getId());
        e = id.getEntries().get(1);
        assertTrue(d2.getTime() == e.getKey().getTime());
        assertEquals(new Integer(2), e.getValue().getId());


        Map<Date, PriceModelWS> m2 = a.unmarshal(id);
        for(Map.Entry<Date, PriceModelWS> e2 : m2.entrySet()) {
            if(e.getKey().equals(d1)) {
                assertEquals(p1, e.getValue());
            } else {
                assertEquals(p2, e.getValue());
                assertTrue(d2.getTime() == e.getKey().getTime());
            }
        }
    }

    public void testCxfMapListDateAdapter() throws Exception {
        CxfMapListDateAdapter a = new CxfMapListDateAdapter();

        Date d = new Date();
        Date d2 = new Date();
        Map<Integer, List<Date>> m = new HashMap<Integer, List<Date>>();
        List l = new ArrayList(2);
        l.add(d);
        l.add(d2);
        m.put(1, l);
        CxfMapListDate id = a.marshal(m);
        assertEquals(1, id.getEntries().size());
        for(BaseCxfMap.KeyValueEntry e : id.getEntries()) {
            assertEquals(new Integer(1), e.getKey());
            List<Date> ld = (List<Date>) e.getValue();
            assertEquals(d, ld.get(0));
            assertEquals(d2, ld.get(1));
        }

        Map<Integer, List<Date>> m2 = a.unmarshal(id);
        for(Map.Entry<Integer,List<Date>> e : m2.entrySet()) {
            assertEquals(new Integer(1), e.getKey());
            List<Date> ld = (List<Date>) e.getValue();
            assertEquals(d, ld.get(0));
            assertEquals(d2, ld.get(1));
        }
    }
}