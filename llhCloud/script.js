var fill = d3.scale.category20();

d3.csv("data.csv", function(data) {
  data.forEach(function(d) {
    d.size = +d.size;
  });
  
  d3.layout.cloud().size([500, 500])
      .words(data)
      .rotate(function() { return ~~(Math.random() * 2) * 90; })
      .font("Impact")
      .fontSize(function(d) { return Math.max(20, Math.min(d.size * 10, 60)); })
      .on("end", draw)
      .start();

  function draw(words) {
    d3.select("body").append("svg")
        .attr("width", 600)
        .attr("height", 600)
      .append("g")
        .attr("transform", "translate(300,300)")
      .selectAll("text")
        .data(data)
      .enter().append("text")
        .style("font-size", function(d) { return d.size + "px"; })
        .style("font-family", "Impact")
        .style("fill", function(d, i) { return fill(i); })
        .attr("text-anchor", "middle")
        .attr("transform", function(d) {
          return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
        })
        .text(function(d) { return d.text; });
  }
});