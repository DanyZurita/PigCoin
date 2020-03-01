package edu.elsmancs.PigCoin;

import java.security.KeyPair;
import java.util.Map;

public class App {

    public static void main( String[] args )
    {
        /**
         * Crea una wallet
         * Genera las claves privada y publica de la wallet 
         */

        System.out.println("\n" + "Ver clave Privada y clave Pública de una wallet" + "\n" + 
                                  "==============================================="        );
                       
        Wallet wallet_1 = new Wallet();
        KeyPair pair = GenSig.generateKeyPair();
        wallet_1.setSK(pair.getPrivate());
        wallet_1.setAddress(pair.getPublic());

        System.out.println("\n Direccion de la Wallet_1: \n" +  wallet_1.getAddress().hashCode());

        /**
         * Crea una segunda wallet, esta vez generando sus claves
         * con un metodo wallet.generateKeyPair() que encapsula
         * el codigo de la anterior historia de usuario
         */

        Wallet wallet_2 = new Wallet();
        wallet_2.generateKeyPair();

        System.out.println("\n Direccion de la Wallet_2: \n" + wallet_2.getAddress().hashCode());

        /**
         * Visualiza las Wallet 1 y 2
         */
        
        System.out.println("\n" + "Ver Wallets 1 y 2" + "\n" + 
                                  "================="        );

        System.out.println("Wallet_1: \n" + wallet_1.toString());
        System.out.println("Wallet_2: \n" + wallet_2.toString());   

        /**
         * Crea una transaccion de pigcoins 
         * Visualiza la transaccion
         */

        System.out.println("\n" + "Ver transaccion" + "\n" +
                                  "==============="        );

        Transaction trx = new Transaction();
        trx = new Transaction("hash_1", "0", wallet_1.getAddress(), wallet_2.getAddress(), 20, "a flying pig!");
        
        System.out.println(trx.toString());

        /**
         * Crea el blockchain
         * y añade transacciones que crean moneda "pigcoins"
         */

        System.out.println("\n" + "Ver BlockChain" + "\n" + 
                                  "=============="        );
        
        // Crea primero la direccion "origen" del sistema que genera los pigcoins
        Wallet origin = new Wallet();
        origin.generateKeyPair();

        BlockChain bChain = new BlockChain();
        trx = new Transaction("hash_1", "0", origin.getAddress(), wallet_1.getAddress(), 20, "bacon eggs");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_2", "1", origin.getAddress(), wallet_2.getAddress(), 10, "spam spam spam");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_3", "hash_1", wallet_1.getAddress(), wallet_2.getAddress(), 20, "a flying pig!");
        bChain.addOrigin(trx);
        
        // Visualiza el blockchain                
        bChain.summarize();
        
        /**
         * Ve la transaccion de una posicion determinada del blockchain
         */
        
        Integer position = 1;
        System.out.println("\n" + "Ver Transaccion en posicion " + position.toString() + " del BlockChain" + "\n" + 
                                  "============================================"        );

        bChain.summarize(position);        

        /**
         * Indicar en la wallet
         * el total de pigcoins que se han enviado,
         * que se han recibido
         * y el balance
         */

        System.out.println("\n" + "Ver el total de pigcoins de las dos wallet" + "\n" + 
                                  "=========================================="        );
        
        wallet_1.loadCoins(bChain);
        System.out.println(wallet_1.toString());

        wallet_2.loadCoins(bChain);
        System.out.println(wallet_2.toString());        

        /**
         * Carga en la wallet el total de transacciones
         * recibidas (aquellas que significan recibir pigcoins)
         * y enviadas (aquellas que envian pigcoins)
         * y mostrarlas
         */
        
        System.out.println("\n" + "Ver las transacciones ENTRANTES de la wallet_1" + "\n" + 
                                  "=============================================="        );
        wallet_1.loadInputTransactions(bChain);
        System.out.println("Wallet = " + wallet_1.getAddress().hashCode());
        System.out.println("Transacciones = " + wallet_1.getInputTransactions().toString());

        System.out.println("\n" + "Ver las transacciones ENVIADAS de la wallet_1" + "\n" + 
                                  "=============================================="        );
        wallet_1.loadOutputTransactions(bChain);
        System.out.println("Wallet = " + wallet_1.getAddress().hashCode());
        System.out.println("Transacciones = " + wallet_1.getOutputTransactions().toString());

        System.out.println("\n" + "Ver las transacciones entrantes de la wallet_2" + "\n" + 
                                  "=============================================="        );
        wallet_2.loadInputTransactions(bChain);
        System.out.println("Wallet = " + wallet_1.getAddress().hashCode());
        System.out.println("Transacciones = " + wallet_2.getInputTransactions().toString());      

        
        /**
         * Recargamos la wallet_1
         * y visualizamos el blockchain
         */
        
        System.out.println("\n" + ">>>>>>>>>>>> RECARGA WALLET_1 >>>>>>>>>>>>" + "\n");
        
        trx = new Transaction("hash_4", "2", origin.getAddress(), wallet_1.getAddress(), 20, "sausages puagh!");
        bChain.addOrigin(trx);
        trx = new Transaction("hash_5", "3", origin.getAddress(), wallet_1.getAddress(), 10, "baked beans are off!");
        bChain.addOrigin(trx);

        System.out.println("\n" + "Ver BlockChain" + "\n" + 
                                  "=============="        );
        bChain.summarize();

        System.out.println("\n" + "Ver el total de pigcoins de las dos wallet" + "\n" + 
                                  "=========================================="        );
        
        wallet_1.loadCoins(bChain);
        wallet_1.loadInputTransactions(bChain);
        wallet_1.loadOutputTransactions(bChain);
        System.out.println(wallet_1.toString());

        wallet_2.loadCoins(bChain);
        wallet_2.loadInputTransactions(bChain);
        wallet_2.loadOutputTransactions(bChain);
        System.out.println(wallet_2.toString());    



        /**
         * Enviar pigcoins de la wallet_1 a la wallet_2
         * Este es el flujo de trabajo que has de programar.
         * 
         * wallet_1.sendCoins(wallet_2.getAddress(), pigcoins, message, bChain) {
         *      
         *          collectCoins(pigcoins);
         *          signTransaction(message);
         *          bChain.processTransactions(pKey_sender, pKey_recipient, consumedCoins, message, signedTransaction);
         *      };
         * 
         * bChain.processTransactions(pKey_sender, pKey_recipient, consumedCoins, message, signedTransaction) {
         *      
         *          isSignatureValid(public_Key, message, signedTransaction)
         *          isConsumedCoinValid(consumedCoins);
         *          createTransaction(pKey_sender, pKey_recipient, consumedCoins,message, signedTransaction);
         *       }
         * 
         * A continuacion se detalla la responsabilidad de cada metodo.
         */
        
        System.out.println("\n" + ">>>>>>>>>>>> Wallet_1 envia transaccion de pigcoins a wallet_2 >>>>>>>>>>>>" + "\n");

        /**
         * Primero has de recolectar los suficientes pigcoins de tu wallet
         * Para ello has de eliminar de las transacciones entrantes de la wallet
         * las que ya se han utilizado para enviar pigcoins
         */       

        /**
         * Los pigcoins son indivisibles, asi que si necesitas 5.2 y no tienes
         * ninguna transaccion entrante exacta de 5.2, has de realizar un CHANGE ADDRESS (CA): 
         * Consiste en reutilizar una transaccion entrante mayor que 5.2, por ejemplo 10,
         * y enviar dos transacciones:
         * - una transaccion de 5.2 al destinatario
         * - otra transaccion de 10 - 5.2 = 4.8 a ti mismo/a
         * Ten cuidado: no puedes utilizar transacciones entrantes (pigcoins)
         * que ya hayas enviado con anterioridad o el blockchain rechazara las nuevas
         */

        Double pigcoins = 25d;
        Map<String, Double> consumedCoins = wallet_1.collectCoins(pigcoins);
        System.out.println("Pigcoins enviados a la wallet_2 y transacciones consumidas: " + consumedCoins);

        /**
         * Una vez que recolectes los pigcoins (transacciones) de tu wallet
         * envialas al blockchain.
         * Debes firmar el mensaje con tu clave privada.
         */
        
        String message = "he roto la hucha :(";
        byte[] signedTransaction = wallet_1.signTransaction(message); // usa GenSig.sign()
        wallet_1.sendCoins(wallet_2.getAddress(), pigcoins, message, bChain); // usa wallet.collectCoins() y bChain.processTransactions()
        
        /**
         *  wallet.sendCoins() invoca a 
         *  bChain.processTransactions(wallet_1.getAddress(), wallet_2.getAddress(), consumedCoins, message, signedTransaction);
         */

        /**
         * El blockchain debe chequear que las transacciones entrantes no proceden
         * de transacciones que ya se han utilizado (gastado), mediante el metodo:
         * 
         * boolean bChain.isConsumedCoinValid(consumedCoins);
         */
                  
        /** 
         * El blockchain debe chequear que las transacciones sean autenticas, 
         * es decir, que la firma del mensaje sea autentica, mediante el metodo:
         * 
         * boolean bChain.isSignatureValid(public_Key, message, signedTransaction)
         * 
         * Este metodo usa GenSig.verify()
         */
        
        /**
         * Si el blockchain comprueba que los pigcoins que envias satisfacen 
         * las dos condiciones anteriores,incluye estas transacciones 
         * en la cadena de bloques 
         * 
         * bChain.processTransactions(wallet_1.getAddress(), wallet_2.getAddress(), consumedCoins, message, signedTransaction);
         * bChain.createTransaction(pKey_sender, pKey_recipient, consumedCoins,message, signedTransaction);
         */

        System.out.println("\n" + "Ver el total de pigcoins de las dos wallet" + "\n" + 
                                  "=========================================="            );
        // comprobamos que la transaccion se ha realizado
        wallet_1.loadCoins(bChain);
        wallet_1.loadInputTransactions(bChain);
        wallet_1.loadOutputTransactions(bChain);
        System.out.println(wallet_1.toString());

        wallet_2.loadCoins(bChain);
        wallet_2.loadInputTransactions(bChain);
        wallet_2.loadOutputTransactions(bChain);
        System.out.println(wallet_2.toString());  

        System.out.println("\n" + "Ver BlockChain" + "\n" + 
                                  "=============="        );  
        bChain.summarize();

        System.out.println("\n" + ">>>>>>>>>>>> Wallet_1 envia transaccion de 100 pigcoins a wallet_2 >>>>>>>>>>>>" + "\n");

        wallet_1.sendCoins(wallet_2.getAddress(), 100d, "no tengo tantos :(", bChain);

        System.out.println("\n" + "Ver el total de pigcoins de las dos wallet" + "\n" + 
                                  "=========================================="            );
        wallet_1.loadCoins(bChain);
        wallet_1.loadInputTransactions(bChain);
        wallet_1.loadOutputTransactions(bChain);
        System.out.println(wallet_1.toString());

        wallet_2.loadCoins(bChain);
        wallet_2.loadInputTransactions(bChain);
        wallet_2.loadOutputTransactions(bChain);
        System.out.println(wallet_2.toString());  

        System.out.println("\n" + ">>>>>>>>>>>> Wallet_1 envia transaccion de 2.5 pigcoins a wallet_2 >>>>>>>>>>>>" + "\n");

        wallet_1.sendCoins(wallet_2.getAddress(), 2.5d, "", bChain);

        System.out.println("\n" + "Ver el total de pigcoins de las dos wallet" + "\n" + 
                                  "=========================================="            );
        wallet_1.loadCoins(bChain);
        wallet_1.loadInputTransactions(bChain);
        wallet_1.loadOutputTransactions(bChain);
        System.out.println(wallet_1.toString());

        wallet_2.loadCoins(bChain);
        wallet_2.loadInputTransactions(bChain);
        wallet_2.loadOutputTransactions(bChain);
        System.out.println(wallet_2.toString());
    }
}
