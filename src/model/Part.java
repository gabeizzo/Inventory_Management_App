package model;

// Supplied class Part.java

/**This class is used to create Part objects.
 */
    public abstract class Part {
        private int id;
        private String name;
        private double price;
        private int stock;
        private int min;
        private int max;

        //Part constructor
        public Part(int id, String name, int stock, double price, int min, int max) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.min = min;
            this.max = max;
        }

        /**This method is used to get the id for Part objects.
         @return id The Part object's name.
         */
        public int getId() {
            return id;
        }

        /**This method is used to set the id for Part objects.
         @param id The id to set.
         */
        public void setId(int id) {
            this.id = id;
        }

        /**This method is used to get the name for Part objects.
         @return name The Part object's name.
         */
        public String getName() {
            return name;
        }

        /**This method is used to set the name for Part objects.
         @param name The name to set.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**This method is used to get the price for Part objects.
         @return the price
         */
        public double getPrice() {
            return price;
        }

        /**This method is used to set the price for Part objects.
         @param price The price to set.
         */
        public void setPrice(double price) {
            this.price = price;
        }

        /**This method is used to get the stock for Part objects.
         @return stock The stock value for a Part object.
         */
        public int getStock() {
            return stock;
        }

        /**This method is used to set the stock for Part objects.
         @param stock The stock to set.
         */
        public void setStock(int stock) {
            this.stock = stock;
        }

        /**This method is used to get the min for Part objects.
         @return min The min value for a Part object.
         */
        public int getMin() {
            return min;
        }

        /**This method is used to set the min for Part objects.
         @param min The min to set.
         */
        public void setMin(int min) {
            this.min = min;
        }

        /** This method is used to get the max for Part objects.
         @return max The max value for a Part object.
         */
        public int getMax() {
            return max;
        }

        /** This method is used to set the max for Part objects.
         @param max The max value to set for a Part object.
         */
        public void setMax(int max) {
            this.max = max;
        }

    }

