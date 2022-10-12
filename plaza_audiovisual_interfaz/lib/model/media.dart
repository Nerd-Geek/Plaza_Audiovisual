class Media {
  String? id;
  double? size;
  String? type;
  String? name;
  String? description;
  int? dimension;

  Media({
    this.id,
    this.size,
    this.type,
    this.name,
    this.description,
    this.dimension,
  });

  Media.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    size = json['size'];
    type = json['type'].obs;
    name = json['name'].obs;
    description = json['description'].obs;
    dimension = json['dimension'].obs;
  }
}