package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = null;
        hale=null;
        antall=0;
        endringer=0;

    }
    public DobbeltLenketListe(T[] a){
        if (a == null){
            throw new NullPointerException();
        }
        if (a.length > 0){
            int i = 0;
            for (;i< a.length;i++){
                if (a[i] != null){
                    hode=new Node<>(a[i]);
                    antall++;
                    break;
                }
            }
            hale = hode ;
            if (hode != null){
                i++;
                for(;i < a.length;i++){
                    if(a[i] != null){
                        hale.neste= new Node<>(a[i],hale,null);
                        hale = hale.neste;
                        antall++;
                    }
                }

            }
        }
    }


    public Liste<T> subliste(int fra, int til) {
        fraTilKontroll(antall,fra,til);

        Liste<T>subliste=new DobbeltLenketListe<>();

        int lengde=til - fra;
        if (lengde<1){
            return subliste;
        }
        Node<T>hjelp= finnNode(fra);
        while (lengde>0){
            subliste.leggInn(hjelp.verdi);
            hjelp=hjelp.neste;
            lengde--;
        }
        return subliste;

    }
    @Override
    public int antall() {
        return antall;
    }
    @Override
    public boolean tom() {
        if (hode== null){
            return true;

        }else {
            return false;
        }
    }
    @Override
    public boolean leggInn(T verdi){
        Objects.requireNonNull(verdi);
        Node<T>nyttNode=new Node<>(verdi);
        //if list is empty.peker head and tail to a new node and antall og chening will be chaneged
        if (hode ==null && hale ==null && antall==0){
            hode = nyttNode;
            hale=nyttNode;
            endringer++;
            antall++;
            return true;
        }
        // her if list is not empty than the tail is the value befor the inserted
        else {
            nyttNode.forrige=hale;
            hale.neste=nyttNode;
            hale=nyttNode;
            endringer++;
            antall++;
            return true;
        }
    }
    @Override
    public String toString(){
        Node<T> midler=hode;
        StringBuilder form= new StringBuilder();
        form.append("[");

        if (tom()){
            form.append("]");
            return form.toString();
        }else {
            form.append(midler.verdi);
            midler =midler.neste;
            while (midler !=null){
                form.append(",");
                form.append(midler.verdi);
                midler= midler.neste;
            }
        }
        form.append("]");
        return form.toString();
    }
    @Override
    public void leggInn(int indes, T verdi){
        Objects.requireNonNull(verdi,"verdei it is null");
        if(indes>antall){
            throw new IndexOutOfBoundsException("indes it's bigger than list length ");
        }else if (indes <0){
            throw new NullPointerException("indes it is nagitiv ");

        }
        //if the list is empty than a new verdi will be both head and tail
        if (antall==0 && indes==0){
            hode =new Node<T>(verdi,null,null);
            hale=hode;
        }else if (indes== antall){
            hale=new Node<>(verdi,hale,null);
            hale.neste.forrige=hode;
        }else if (indes==0){
            hode=new Node<>(verdi,null,hode);
            hode.neste.forrige=hode;
        }
        else {
            Node<T> midler=hode;
            for (int i = 0;i<indes;i++) midler= midler.neste;{
                midler = new Node<T>(verdi,midler.forrige,midler);
            }
            midler.neste.forrige=midler.forrige.neste=midler;

        }
        antall++;
        endringer++;
    }

    private Node<T> finnNode(int indeks) {
        //sjekker om indeks finnes i listen, returnerer false hvis ikke.
        indeksKontroll(indeks, false);
        Node<T> midlertidig;

        //hvis indeks er mindre enn liste/2 starter fra hode og setter hode til midlertidig node og setter pekeren til midlertidig.neste i hver iterasjon. stopper loopen på indeks.
        if (indeks < antall / 2) {
            midlertidig = hode;
            for (int i = 0; i < indeks; i++) {
                midlertidig = midlertidig.neste;
            }
            return midlertidig;
        }
        // Hvis indeks er større enn liste/2 starter fra hale og setter hale til midlertidig node og setter pekeren til midlertidig.forrige i hver iterasjon. stopper loopen ved indeks.
        else {
            midlertidig = hale;
            for (int i = antall-1; i > indeks; i--) {
                midlertidig = midlertidig.forrige;
            }
            return midlertidig;
        }
    }

    private void fraTilKontroll(int tabellLengde, int fra, int til) {
        if (fra < 0 || til > tabellLengde) {
            throw new IndexOutOfBoundsException();
        }
        if (fra > til) {
            throw new IllegalArgumentException();
        }
    }






    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi)!=-1;
    }

    @Override
    public T hent(int indeks) {
        Node<T>midlertidlig =finnNode(indeks);
        return midlertidlig.verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if (verdi == null) {
            return -1;
        }
        Node<T>midlertidig=hode;
        for (int i = 0; i<antall; i++,midlertidig=midlertidig.neste){
            if (midlertidig.verdi.equals(verdi)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi);
        Node<T>midlertid=finnNode(indeks);
        T gamle = midlertid.verdi;
        endringer++;
        midlertid.verdi=nyverdi;
        return gamle;

    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }


    public String omvendtString() {
        Node<T>midler=hale;
        StringBuilder form=new StringBuilder();
        form.append("[");
        if (tom()){
            form.append("]");
            return form.toString();
        }
        else {
            form.append(midler.verdi);
            midler=midler.forrige;
            while (midler !=null){
                form.append(",");
                form.append(midler.verdi);
                midler=midler.forrige;
            }
        }
        form.append("]");
        return toString();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


