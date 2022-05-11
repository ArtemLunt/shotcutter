"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.MoviesUpdate = void 0;
const movies_service_1 = require("./movies.service");
const nestjs_telegraf_1 = require("nestjs-telegraf");
const common_1 = require("@nestjs/common");
const telegraf_1 = require("telegraf");
let MoviesUpdate = class MoviesUpdate {
    onText(ctx) {
        var _a, _b;
        this._moviesService.lookup((_b = (_a = ctx.update) === null || _a === void 0 ? void 0 : _a.message) === null || _b === void 0 ? void 0 : _b.text)
            .subscribe(async (movies) => {
            for (let movie of movies) {
                await ctx.replyWithHTML(`<b>${movie.title}</b>` +
                    `\n${this._getPreformattedGenresString(movie.genres)}`);
                await ctx.replyWithPhoto(`https://image.tmdb.org/t/p/w500${movie.posterPath}`);
                await ctx.reply(`\n\n${movie.overview}`, {
                    reply_markup: {
                        inline_keyboard: [
                            [{ text: 'Open in browser', url: 'vk.com' }]
                        ]
                    }
                });
            }
        });
    }
    _getPreformattedGenresString(genres) {
        return genres.map(genre => `<i>${genre}</i>`).join(', ');
    }
};
__decorate([
    (0, common_1.Inject)(movies_service_1.MoviesService),
    __metadata("design:type", movies_service_1.MoviesService)
], MoviesUpdate.prototype, "_moviesService", void 0);
__decorate([
    (0, nestjs_telegraf_1.Command)('lookup'),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [telegraf_1.Context]),
    __metadata("design:returntype", void 0)
], MoviesUpdate.prototype, "onText", null);
MoviesUpdate = __decorate([
    (0, nestjs_telegraf_1.Update)()
], MoviesUpdate);
exports.MoviesUpdate = MoviesUpdate;
//# sourceMappingURL=movies.update.js.map