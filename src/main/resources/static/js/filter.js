$(function() {
    $('#filter-button').on("click", function() {filter()});
    $('#reset-button').on("click", function() {reset()});
    $('#reset-button').hide();
    $('#sort_by').on("change", function() {sortBy()});

    function filter() {
        $('#reset-button').show();
        filterByFirstname();
        filterByLastName();
        filterByCity();
        filterByPrice();
    }

    function reset() {
        $('.block').each(function (index) {
            $(this).show();
        });

        var $maxRange = $('#max-range');
        var $minRange = $('#min-range');
        var minRange = $minRange.attr('min');
        var maxRange = $maxRange.attr('max');
        $maxRange.val(maxRange);
        $minRange.val(minRange);
        $('#max_price').val(maxRange);
        $('#min_price').val(minRange);
        $('#firstname-filter').val('');
        $('#lastname-filter').val('');
        $('#city-filter').val('');
        $('#reset-button').hide();
    }

    function sortBy() {
        var isChecked = $('#sort_by').prop('checked');

        var $elements = $('.rate-val');
        hideAll($elements);

        if (isChecked) {
            $elements = sortFromMax($elements);
        } else {
            $elements = sortFromMin($elements);
        }

        showAll($elements);

    }

    function sortFromMin($elements) {
        console.log('before');
        $elements.each(function(index) {
            console.log(this.value);
        });

        $elements.sort(function (a, b) {
            return parseInt(a.value) - parseInt(b.value);
        });

        console.log('after');
        $elements.each(function(index) {
            console.log(this.value);
        });


        return $elements;
    }

    function sortFromMax($elements) {
        console.log('before');
        $elements.each(function(index) {
            console.log(this.value);
        });


        $elements.sort(function (a, b) {
            return parseInt(b.value) - parseInt(a.value);
        });

        console.log('after');

        $elements.each(function(index) {
            console.log(this.value);
        });


        return $elements;
    }

    function hideAll($elements) {
        $elements.each(function (index) {
            var parent = $(this).parents()[1];

            $(parent).remove();
        });
    }

    function showAll($elements) {
        console.log("show");
        $elements.each(function (index) {
            var parent = $(this).parents()[1];
            console.log(this.value);
            $('#doctors').append(parent);
            // $(parent).show();
        });
    }

    function filterByFirstname() {
        var filter = $('#firstname-filter').val();
        var $firstnames = $('.firstname-val');

        $firstnames.each(function (index) {
            var text = $(this).text();

            if (text.toLowerCase().indexOf(filter) < 0) {
                $($(this).parents()[2]).hide();
            }
        });
    }

    function filterByLastName() {
        var filter = $('#lastname-filter').val();
        var $firstnames = $('.lastname-val');

        $firstnames.each(function (index) {
            var text = $(this).text();

            if (text.toLowerCase().indexOf(filter) < 0) {
                $($(this).parents()[2]).hide();
            }
        });
    }

    function filterByCity() {
        var filter = $('#city-filter').val();
        var $firstnames = $('.city-val');

        $firstnames.each(function (index) {
            var text = $(this).text();

            if (text.toLowerCase().indexOf(filter) < 0) {
                $($(this).parents()[2]).hide();
            }
        });
    }

    function filterByPrice() {
        var minRange = parseInt($('#min-range').val());
        var maxRange = parseInt($('#max-range').val());

        var $prices = $('.price-val');

        $prices.each(function (index) {
            var price = $(this).text();

            if (price > maxRange || price < minRange) {
                $($(this).parents()[2]).hide();
            }
        })
    }
});

function showMin(val) {
    $('#min_price').val(val);
}

function showMax(val) {
    $('#max_price').val(val);
}