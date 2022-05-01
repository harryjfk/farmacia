//* detect touch devices 
function is_touch_device() {
    return !!('ontouchstart' in window);
}

$(function() {

//* make active on accordion change
    $('#side_accordion').on('hidden.bs.collapse shown.bs.collapse', function() {
        gebo_sidebar.make_active();
        gebo_sidebar.scrollbar();
    });
    //* resize elements on window resize
    var lastWindowHeight = $(window).height();
    var lastWindowWidth = $(window).width();
    $(window).on("debouncedresize", function() {
        if ($(window).height() != lastWindowHeight || $(window).width() != lastWindowWidth) {
            lastWindowHeight = $(window).height();
            lastWindowWidth = $(window).width();
            //gebo_sidebar.update_scroll();
            if (!is_touch_device()) {
                $('.sidebar_switch').qtip('hide');
            }
        }
    });

    gebo_nav_mouseover.init();
    gebo_submenu.init();

    //* sidebar
    gebo_sidebar.init();
    gebo_sidebar.make_active();
});

gebo_sidebar = {
    init: function() {
        // sidebar onload state
        if ($(window).width() > 979) {
            if (!$('body').hasClass('sidebar_hidden')) {
                if ($.cookie('gebo_sidebar') == "hidden") {
                    $('body').addClass('sidebar_hidden');
                    $('.sidebar_switch').toggleClass('on_switch off_switch').attr('title', 'Show Sidebar');
                }
            } else {
                $('.sidebar_switch').toggleClass('on_switch off_switch').attr('title', 'Show Sidebar');
            }
        } else {
            $('body').addClass('sidebar_hidden');
            $('.sidebar_switch').removeClass('on_switch').addClass('off_switch');
        }

        gebo_sidebar.info_box();
        //* sidebar visibility switch
        $('.sidebar_switch').click(function() {
            $('.sidebar_switch').removeClass('on_switch off_switch');
            if ($('body').hasClass('sidebar_hidden')) {
                $.cookie('gebo_sidebar', null);
                $('body').removeClass('sidebar_hidden');
                $('.sidebar_switch').addClass('on_switch').show();
                $('.sidebar_switch').attr('title', "Hide Sidebar");
            } else {
                $.cookie('gebo_sidebar', 'hidden');
                $('body').addClass('sidebar_hidden');
                $('.sidebar_switch').addClass('off_switch');
                $('.sidebar_switch').attr('title', "Show Sidebar");
            }
            ;
            gebo_sidebar.info_box();
            //gebo_sidebar.update_scroll();
            $(window).resize();
        });
        //* prevent accordion link click
        $('.sidebar .accordion-toggle').click(function(e) {
            e.preventDefault();
        });
        $(window).on("debouncedresize", function(event) {
            gebo_sidebar.scrollbar();
        });
    },
    info_box: function() {
        var s_box = $('.sidebar_info');
        var s_box_height = s_box.actual('height');
        s_box.css({
            'height': s_box_height
        });
        $('.push').height(s_box_height);
        $('.sidebar_inner').css({
            'margin-bottom': '-' + s_box_height + 'px',
            'min-height': '100%'
        });
    },
    make_active: function() {
        var thisAccordion = $('#side_accordion');
        thisAccordion.find('.panel-heading').removeClass('sdb_h_active');
        var thisHeading = thisAccordion.find('.panel-body.in').prev('.panel-heading');
        if (thisHeading.length) {
            thisHeading.addClass('sdb_h_active');
        }
    },
    scrollbar: function() {
        if ($('.sidebar_inner_scroll').length) {
            $('.sidebar_inner_scroll').slimScroll({
                position: 'left',
                height: 'auto',
                alwaysVisible: true,
                opacity: '0.2',
                wheelStep: is_touch_device() ? 40 : 1
            });
        }
    }
};

gebo_nav_mouseover = {
    init: function() {
        $('header li.dropdown').mouseenter(function() {
            if ($('body').hasClass('menu_hover')) {
                $(this).addClass('navHover');
            }
        }).mouseleave(function() {
            if ($('body').hasClass('menu_hover')) {
                $(this).removeClass('navHover open');
            }
        });
        $('header li.dropdown > a').click(function() {
            if ($('body').hasClass('menu_hover')) {
                window.location = $(this).attr('href');
            }
        });
    }
};
gebo_submenu = {
    init: function() {
        $('.dropdown-menu li').each(function() {
            var $this = $(this);
            if ($this.children('ul').length) {
                $this.addClass('sub-dropdown');
                $this.children('ul').addClass('sub-menu');
            }
        });
        $('.sub-dropdown').on('mouseenter', function() {
            $(this).addClass('active').children('ul').addClass('sub-open');
        }).on('mouseleave', function() {
            $(this).removeClass('active').children('ul').removeClass('sub-open');
        });
    }
};