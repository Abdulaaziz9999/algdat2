package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


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
        hale = null;
        antall = 0;
        endringer = 0;

    }

    public DobbeltLenketListe(T[] a) {
        if (a == null) {
            throw new NullPointerException();
        }
        if (a.length > 0) {
            int i = 0;
            for (; i < a.length; i++) {
                if (a[i] != null) {
                    hode = new Node<>(a[i]);
                    antall++;
                    break;
                }
            }
            hale = hode;
            if (hode != null) {
                i++;
                for (; i < a.length; i++) {
                    if (a[i] != null) {
                        hale.neste = new Node<>(a[i], hale, null);
                        hale = hale.neste;
                        antall++;
                    }
                }

            }
        }
    }


    public Liste<T> subliste(int fra, int til) {
        fraTilKontroll(antall, fra, til);

        Liste<T> subliste = new DobbeltLenketListe<>();

        int lengde = til - fra;
        if (lengde < 1) {
            return subliste;
        }
        Node<T> hjelpeNode = finnNode(fra);
        while (lengde > 0) {
            subliste.leggInn(hjelpeNode.verdi);
            hjelpeNode = hjelpeNode.neste;
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
        if (hode == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi);
        Node<T> nyttNode = new Node<>(verdi);
        //if list is empty.peker head and tail to a new node and antall og chening will be chaneged
        if (tom()) {
            hode = nyttNode;
            hale = nyttNode;
            endringer++;
            antall++;
            return true;
        }
        // her if list is not empty than the tail is the value befor the inserted
        else {
            nyttNode.forrige = hale;
            hale.neste = nyttNode;
            hale = nyttNode;
            endringer++;
            antall++;
            return true;
        }


    }

    @Override
    public String toString() {
        Node<T> midler = hode;
        StringBuilder form = new StringBuilder();
        form.append("[");

        if (tom()) {
            form.append("]");
            return form.toString();
        } else {
            form.append(midler.verdi);
            midler = midler.neste;
            while (midler != null) {
                form.append(",");
                form.append(midler.verdi);
                midler = midler.neste;
            }
        }
        form.append("]");
        return form.toString();
    }

    @Override
    public void leggInn(int index, T verdi) {
        Objects.requireNonNull(verdi, "verdi it is null");


        if (index > antall) {
            throw new IndexOutOfBoundsException("index store enn  list lengde");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index er  negativ ");

        }
        if (antall == 0 && index == 0) {
            hode = new Node<>(verdi, null, null); // hvis listen er tom fra før
            hale = hode;

        } else if (index == antall) {
            hale = new Node<>(verdi, hale, null);
            hale.forrige.neste = hale;
        } else if (index == 0) {
            hode = new Node<>(verdi, null, hode);
            hode.neste.forrige = hode;
        } else {
            Node<T> tempratory = hode;
            for (int i = 0; i < index; i++) tempratory = tempratory.neste;
            {
                tempratory = new Node<T>(verdi, tempratory.forrige, tempratory);

            }
            tempratory.neste.forrige = tempratory.forrige.neste = tempratory;
        }
        antall++;
        endringer++;

    }

    private Node<T> finnNode(int indeks) {
        //sjekker om indeks finnes i listen, returnerer false hvis ikke.
        indeksKontroll(indeks, false);
        Node<T> temprator;
        if (indeks < antall / 2) {
            temprator = hode;
            for (int i = 0; i < indeks; i++) {
                temprator = temprator.neste;
            }
            return temprator;
        } else {
            temprator = hale;
            for (int i = antall - 1; i > indeks; i--) {
                temprator = temprator.forrige;
            }
            return temprator;
        }
    }


    private void fraTilKontroll(int tabellLengde, int fra, int til) {
        if (fra < 0 || til > tabellLengde) {
            throw new IndexOutOfBoundsException();
        }
        if (fra > til) {
            throw new IllegalStateException();
        }

    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks) {
        Node<T> temp = finnNode(indeks);
        return temp.verdi;
    }


    @Override
    public int indeksTil(T verdi) {
        if (verdi == null) {
            return -1;

        }
        return 0;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi);
        Node<T> temp = finnNode(indeks);

        T eldereVerdi = temp.verdi;
        endringer++;


        temp.verdi = nyverdi;
        return eldereVerdi;

    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) {
            return false;
        }
        Node<T> temper = hode;

        if (verdi.equals(temper.verdi)) {
            if (temper.neste != null) {
                hode = temper.neste;
                hode.forrige = null;
            } else {
                hode = null;
                hale = null;
            }
            antall++;
            endringer++;
            return true;
        }
        temper = hale;
        if (verdi.equals(temper.verdi)) {
            hale = temper.forrige;
            hale.neste = null;
            antall--;
            endringer++;
            return true;
        }
        temper = hode.neste;
        for (; temper != null; temper = temper.neste) {
            if (verdi.equals(temper.verdi)) {
                temper.forrige.neste = temper.neste;
                temper.neste.forrige = temper.forrige;
                antall--;
                endringer++;
                return true;
            }
        }
        return false;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);
        Node<T> temper = hode;
        T verdi;


        if (indeks == 0) {
            verdi = temper.verdi;
            if (temper.neste != null) {
                hode = temper.neste;
                hode.forrige = null;

            } else {
                hode = null;
                hode = null;
            }
        } else if (indeks == antall - 1) {
            temper = hale;
            verdi = hale.verdi;
            hale = temper.forrige;
            hale.neste = null;

        } else {
            for (int i = 0; i < indeks; i++) {
                temper = temper.neste;
            }
            verdi = temper.verdi;
            temper.forrige.neste = temper.neste;

            temper.neste.forrige = temper.forrige;

        }
        antall--;
        endringer++;
        return verdi;
    }


    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }


    public String omvendtString() {
        Node<T> temp = hale;
        StringBuilder form = new StringBuilder();
        form.append("[");
        //checks if the list is empty  then returns an empty string it is ;
        if (tom()) {
            form.append("]");
            form.toString();
            //traveres the list backwords,the node points to the previous node in each iteration and appends the values
        } else {
            form.append(temp.verdi);
            temp = temp.forrige;
            while (temp != null) {
                form.append(",");
                form.append(temp.verdi);
                temp = temp.forrige;
            }
        }
        form.append("]");
        return form.toString();

    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
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
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException(" within values ");

            if (endringer != iteratorendringer) {
                throw new ConcurrentModificationException("error in the number of changes");

                T temp = denne.verdi;
                denne = denne.neste;
                fjernOK = true;
                return temp;
            }


            @Override
            public void remove() {
                Node<T> p = (denne == null ? hale : denne.forrige);
                if (!fjernOK) {
                    throw new IllegalStateException("you kan remov any elment  now");
                }
                if (iteratorendringer != endringer) {
                    throw new ConcurrentModificationException("the list is chang ");

                }
                fjernOK = false;

                if (p == hode) {
                    if (antall == 1) {
                        hode = hale = null;
                    }
                    else {hode = hode.neste;hode.forrige = null;}
                }
                else if (p == hale) {
                    hale = hale.forrige;
                    hale.neste = null;
                } else {
                    p.forrige.neste = p.neste;
                    p.neste.forrige = p.forrige;
                }
                antall--;
                iteratorendringer++;
                endringer++;
            }

        } // class DobbeltLenketListeIterator

        public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
            if (liste.tom()) return;
            for (int i = 0; i < liste.antall(); i++) {
                for (int k = 0; k < liste.antall(); k++) {
                    if ((c.compare(liste.hent(i), liste.hent(k))) < 0)
                    {
                        T temp = liste.hent(i);
                        liste.oppdater(i, liste.hent(k));
                        liste.oppdater(k, temp);
                    }
                }

            }

        }

    }// class DobbeltLenketListe


