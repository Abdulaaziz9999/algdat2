package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


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
        if (hode==null){
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public boolean leggInn(T verdi){
        Objects.requireNonNull(verdi);
        Node<T>nyttNode=new Node<>(verdi);
        //if list is empty.peker head and tail to a new node and antall og chening will be chaneged
        if (tom()){
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
    public void leggInn(int index, T verdi){

    }

    private Node<T> finnNode(int indeks) {
        //sjekker om indeks finnes i listen, returnerer false hvis ikke.
        indeksKontroll(indeks,false);
        Node<T>temprator;
        if (indeks<antall/2){
            temprator=hode;
            for (int i = 0;i<indeks;i++){
                temprator=temprator.neste;
            }
            return temprator;
        }else {
            temprator=hale;
            for(int i = antall -1; i<indeks;i--){
                temprator=temprator.forrige;
            }
            return temprator;
        }


    private void fraTilKontroll(int tabellLengde, int fra, int til) {

    }






    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi)!=-1;
    }

    @Override
    public T hent(int indeks) {
            Node<T>temp = finnNode(indeks);
            return temp.verdi;
        }

    }

    @Override
    public int indeksTil(T verdi) {

    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi);
        Node<T>temp= finnNode(indeks);

        T eldereVerdi= temp.verdi;
        endringer++;


        temp.verdi=nyverdi;
        return eldereVerdi;

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
        Node<T>temp=hale;
        StringBuilder form= new StringBuilder();
        form.append("[");
        //checks if the list is empty  then returns an empty string it is ;
        if (tom()){
            form.append("]");
            form.toString();
            //traveres the list backwords,the node points to the previous node in each iteration and appends the values
        }
        else {
            form.append(temp.verdi);
            temp=temp.forrige;
            while (temp !=null){
                form.append(",");
                form.append(temp.verdi);
                temp=temp.forrige;
            }
        }
        form.append("]");
        return form.toString();

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


